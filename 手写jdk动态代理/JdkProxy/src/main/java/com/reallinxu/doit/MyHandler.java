package com.reallinxu.doit;

import com.reallinxu.common.ProxyObjectInte;

import java.lang.reflect.Method;

public class MyHandler implements MyInvocationHandler {

    private ProxyObjectInte proxyObject;

    MyHandler(ProxyObjectInte proxy){
        this.proxyObject = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(proxyObject, args);
        after();
        return invoke;
    }

    private void before(){
        System.out.println("代理前。。。");
    }

    private void after(){
        System.out.println("代理后。。。");
    }
}
