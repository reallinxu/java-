package com.reallinxu.doit;


/**
 * jdk:1.8
 * 通过子类引用父类的静态字段,不会导致子类初始化
 */
class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;

}

class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }

    public static int value1 = 456;
}

public class Test {
    public static void main(String[] args) throws Exception {
        SuperClass s = new SuperClass();
        //输出:
        //SuperClass init!
        //123

    }
}


