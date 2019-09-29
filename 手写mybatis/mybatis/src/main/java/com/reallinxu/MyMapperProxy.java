package com.reallinxu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyMapperProxy implements InvocationHandler {

    private MySqlSession sqlSession;

    public MyMapperProxy(MySqlSession sqlSession){this.sqlSession = sqlSession;}

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String mapperClass = method.getDeclaringClass().getName();

        if(StudentMapperXML.namespace.equals(mapperClass)){
            String methodName = method.getName();
            String originSql = StudentMapperXML.getMethodSql(methodName);

            String formatterSql = String.format(originSql,String.valueOf(args[0]));

            return sqlSession.selectOne(formatterSql);
        }

        return null;
    }
}
