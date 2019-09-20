package com.reallinxu.servlet;


import com.reallinxu.anno.Autowired;
import com.reallinxu.anno.Controller;
import com.reallinxu.anno.RequestMapping;
import com.reallinxu.anno.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author linxu
 * <p>
 * servlet3.0不再需要配置xml可以通过@WebServlet注解配置servlet
 * load-on-startup 元素标记容器是否应该在启动的时候加载这个servlet，(实例化并调用其init()方法)。
 * 它的值必须是一个整数，表示servlet应该被载入的顺序
 * 如果该元素不存在或者这个数为负时，则容器会当该Servlet被请求时，再加载。
 * 当值为0或者大于0时，表示容器在应用启动时就加载并初始化这个servlet；
 * 正数的值越小，该servlet的优先级越高，应用启动时就越先加载。
 * 当值相同时，容器就会自己选择顺序来加载。(当然我写了几个测试程序，没有抓住它的规律性，我们暂且理解为容器自主选择！)
 */
@WebServlet(name = "dispatcherServlet", urlPatterns = "/*", loadOnStartup = 1, initParams = {@WebInitParam(name = "base-package", value = "com.reallinxu")})
public class DispatcherServlet extends HttpServlet {

    /**
     * 扫描基包路径
     */
    private String basePackage = "";

    /**
     * 所有的对象全限定名
     */
    private List<String> packageNames = new ArrayList<>();

    /**
     * 注解名称：实例化对象
     */
    private Map<String, Object> instansMap = new HashMap<String, Object>();

    /**
     * URl地址:方法
     */
    private Map<String, Method> urlMethodMap = new HashMap<String, Method>();

    /**
     * 方法:对象全限定名 用于反射
     */
    private Map<Method, String> methodpackageMap = new HashMap<Method, String>();

    @Override
    public void init(ServletConfig config) throws ServletException {

        basePackage = config.getInitParameter("base-package");

        try {
//        1. 我们应该去扫描基包下的类，得到信息A
            scanBasePackage(basePackage);
//        2. 对于@Controller/@Service注解而言，我们需要拿到对应的名称，并初始化它们修饰的类，形成映射关系B
            instansPackageName();
//        3. 我们还得扫描类中的字段，如果发现有@Autowired的话，我们需要完成注入
            springIOC();
//        4.我们还需要扫描@RequestMapping，完成URL到某一个Controller的某一个方法上的映射关系C
            handlerRequestMappingMethod();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handlerRequestMappingMethod() {
        if (packageNames.isEmpty()) {
            return;
        }
        for (Map.Entry<String,Object> entry: instansMap.entrySet()) {
            if(entry.getValue().getClass().isAnnotationPresent(Controller.class)){
                for(Method method : entry.getValue().getClass().getMethods()){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String value = requestMapping.value();
                        urlMethodMap.put(value,method);
                        methodpackageMap.put(method,entry.getKey());
                    }
                }
            }
        }
    }

    private void springIOC() throws IllegalAccessException {
        if (packageNames.isEmpty()) {
            return;
        }

        for (Map.Entry<String,Object> entry: instansMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(Autowired.class)){
                    field.setAccessible(true);
                    field.set(entry.getValue(),instansMap.get(field.getName()));
                }
            }
        }
    }

    private void instansPackageName() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (packageNames.isEmpty()) {
            return;
        }

        for (String packageName : packageNames) {
            Class clazz = Class.forName(packageName);
            if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
                //类名首字母小写
                String value = convFirstUp(clazz.getSimpleName());
                instansMap.put(value, clazz.newInstance());
                System.out.println("初始化Bean : " + value);
            }
        }
    }

    private String convFirstUp(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    private void scanBasePackage(String basePackage) {
        String aa = basePackage.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(aa);
        File basePackageFile = new File(url.getPath());
        for (File file : basePackageFile.listFiles()) {
            if (file.isDirectory()) {
                scanBasePackage(basePackage + "." + file.getName());
            } else if (file.isFile()) {
                //去掉后缀
                packageNames.add(basePackage + "." + file.getName().split("\\.")[0]);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        Method method = urlMethodMap.get(requestURI);
        if(method == null){
            resp.getWriter().write("404 not found");
            return;
        }

        try {
            method.setAccessible(true);
            Object invoke = method.invoke(instansMap.get(methodpackageMap.get(method)));
            resp.getWriter().write(invoke.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
