package com.reallinxu.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        1. 我们应该去扫描基包下的类，得到信息A
        scanBasePackage(basePackage);

//        2. 对于@Controller/@Service/@Repository注解而言，我们需要拿到对应的名称，并初始化它们修饰的类，形成映射关系B

//        3. 我们还得扫描类中的字段，如果发现有@Autowired的话，我们需要完成注入

//        4.我们还需要扫描@RequestMapping，完成URL到某一个Controller的某一个方法上的映射关系C


    }

    private void scanBasePackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        File basePackageFile = new File(url.getPath());
        for (File file : basePackageFile.listFiles()) {
            if(file.isDirectory()){
                scanBasePackage(basePackageFile + "."+ file.getName());
            }else if(file.isFile()){
                //去掉后缀
                packageNames.add(basePackage + "." + file.getName().split(".")[0]);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
