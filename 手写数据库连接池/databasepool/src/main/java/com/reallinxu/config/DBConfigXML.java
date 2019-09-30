package com.reallinxu.config;

public class DBConfigXML {

    public static final String jdbcURL = "jdbc:mysql://localhost:3306/test";
    public static final String jdbcUserName = "root";
    public static final String jdbcPassWord = "123456";

    //数据库连接池初始化大小
    public static final int initCount = 10;

    //连接池不足增长步进值
    public static final int step = 2;

    //连接池最大数量
    public static final int maxCount = 50;


}
