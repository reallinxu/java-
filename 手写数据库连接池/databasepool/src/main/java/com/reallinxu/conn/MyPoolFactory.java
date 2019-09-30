package com.reallinxu.conn;

public class MyPoolFactory {

    public static class CreateMyPool {
        public static IMyPool myPool = new MyDefaultPool();
    }

    public static IMyPool getInstance() {
        return CreateMyPool.myPool;
    }
}
