package com.reallinxu;

public interface MySqlSession {

    <T> T selectOne(String var1);

    <T> T getMapper(Class<T> var1);
}
