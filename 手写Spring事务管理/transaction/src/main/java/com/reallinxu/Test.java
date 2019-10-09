package com.reallinxu;

import org.apache.commons.dbcp.BasicDataSource;

public class Test {

    public static final String jdbcDriver = "com.mysql.jdbc.Driver";
    public static final String jdbcURL = "jdbc:mysql://localhost:3306/test";
    public static final String jdbcUserName = "root";
    public static final String jdbcPassWord = "123456";

    public static void main(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(jdbcDriver);
        basicDataSource.setUsername(jdbcUserName);
        basicDataSource.setPassword(jdbcPassWord);
        basicDataSource.setUrl(jdbcURL);

        final StudentService studentService = new StudentService(basicDataSource);

        //模拟并发请求
        for (int i = 0; i < 10; i++) {
            new Thread((Runnable) () -> {
                studentService.action();
            }).start();
        }

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
