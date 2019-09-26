package com.reallinxu.jdkproxy;

import com.reallinxu.common.ProxyObject;
import com.reallinxu.common.ProxyObjectInte;

import java.lang.reflect.Proxy;

public class TestJdkProxy {
    public static void main(String[] args) throws Throwable {
        ProxyObjectInte proxyObject = new ProxyObject();
        TestHandler myHandler = new TestHandler(proxyObject);
        ProxyObjectInte f = (ProxyObjectInte) Proxy.newProxyInstance(ProxyObjectInte.class.getClassLoader(), new Class[]{ProxyObjectInte.class}, myHandler);
        f.doSomeThing();

    }
}
