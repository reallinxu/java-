package com.reallinxu;

import java.lang.reflect.Proxy;

public class MyDefautSqlSession implements MySqlSession{

    public MyExecutor myExecutor = new MyBaseExecutor();

    public <T> T selectOne(String sql) {
        return myExecutor.query(sql);
    }

    public <T> T getMapper(Class<T> interfaces) {
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces},new MyMapperProxy(this));
    }
}
