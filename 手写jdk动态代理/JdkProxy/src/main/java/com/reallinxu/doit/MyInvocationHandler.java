package com.reallinxu.doit;

import java.lang.reflect.Method;

/**
 * 自定义invocationHandler
 */
public interface MyInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
