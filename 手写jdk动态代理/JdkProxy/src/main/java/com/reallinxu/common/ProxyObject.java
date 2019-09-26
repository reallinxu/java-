package com.reallinxu.common;

public class ProxyObject implements ProxyObjectInte{

    @Override
    public void doSomeThing() throws Throwable{
        System.out.println("这是被代理的小弟。。。。");
    }

//    @Override
//    public void eat(String food) {
//        System.out.println("吃"+food);
//    }


}
