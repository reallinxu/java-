package com.reallinxu.jdkproxy;

import com.reallinxu.common.ProxyObjectInte;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestHandler implements InvocationHandler {

    private ProxyObjectInte proxyObject;

    TestHandler(ProxyObjectInte proxy){
        this.proxyObject = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(proxyObject,args);
        after();
        return null;
    }

    private void before(){
        System.out.println("代理前。。。");
    }

    private void after(){
        System.out.println("代理后。。。");
    }

}
