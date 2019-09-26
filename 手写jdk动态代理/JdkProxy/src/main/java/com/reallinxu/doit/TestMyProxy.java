package com.reallinxu.doit;

import com.reallinxu.common.ProxyObject;
import com.reallinxu.common.ProxyObjectInte;
import com.reallinxu.jdkproxy.TestHandler;

import java.lang.reflect.Proxy;

public class TestMyProxy {
    public static void main(String[] args) throws Throwable {
        ProxyObjectInte proxyObject = new ProxyObject();
        MyHandler myHandler = new MyHandler(proxyObject);
        MyClassLoader myClassLoader = new MyClassLoader("D:\\java成长之路\\手写jdk动态代理\\JdkProxy\\src\\main\\java\\com\\reallinxu\\doit","NewProxy");
        ProxyObjectInte f = (ProxyObjectInte) MyProxy.newProxyInstance(myClassLoader, ProxyObjectInte.class, myHandler);
        f.doSomeThing();
    }

}
