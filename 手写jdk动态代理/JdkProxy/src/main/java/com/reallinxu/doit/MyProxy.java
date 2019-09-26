package com.reallinxu.doit;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyProxy {
    private static final String rt = "\r";

    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?> interfaces,
                                          MyInvocationHandler h)
            throws IllegalArgumentException {
        if (h == null) {
            throw new NullPointerException();
        }
        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassString = new StringBuffer();
        proxyClassString.append("package ").append(loader.getProxyClassPackage()).append(";").append(rt)
                .append("import java.lang.reflect.Method;").append(rt)
                .append("import com.reallinxu.doit.MyInvocationHandler;").append(rt)
                .append("public class $MyProxy0 implements ").append(interfaces.getName()).append("{").append(rt)
                .append("MyInvocationHandler h;").append(rt)
                .append("public $MyProxy0(MyInvocationHandler h){").append(rt).append("this.h = h;}").append(rt)
                .append(getMethodString(methods,interfaces)).append("}");

        String fileName = loader.getDir() + File.separator + "$MyProxy0.java";
        File myProxyFile = new File(fileName);
        try{
            compile(proxyClassString,myProxyFile);
            Class $myProxy0 = loader.findClass("$MyProxy0");
            Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);
            Object o = constructor.newInstance(h);
            return o;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void compile(StringBuffer proxyClassString,File myProxyFile) throws IOException{
        FileWriter fileWriter = new FileWriter(myProxyFile);
        fileWriter.write(proxyClassString.toString());
        fileWriter.flush();
        fileWriter.close();
        // 编译Java文件为class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = fileManager.getJavaFileObjects(myProxyFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, iterable);
        task.call();
        fileManager.close();
    }


    private static String getMethodString(Method[] methods,Class interfaces){
        StringBuffer methodStringBuffer = new StringBuffer();

        for (Method method : methods){
            methodStringBuffer.append("public void ").append(method.getName())
                    .append(" () ").append("throws Throwable{ ")
                    .append("Method method1 = ").append(interfaces.getName())
                    .append(".class.getMethod( \"").append(method.getName())
                    .append("\",new Class[]{});")
                    .append("this.h.invoke(this,method1,null);}").append(rt);
        }
        return methodStringBuffer.toString();
    }
}
