package com.reallinxu;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class StudentDao {

    private DataSource dataSource;

    public StudentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert() throws SQLException {
        Connection connection = SingleThreadConnectionHolder.getConnection(dataSource);

        //业务操作
        System.out.println("当前用户线程：" + Thread.currentThread().getName() + " 使用管道：" + connection.hashCode());
    }

    public void update() throws SQLException {
        Connection connection = SingleThreadConnectionHolder.getConnection(dataSource);

        //业务操作
        System.out.println("当前用户线程：" + Thread.currentThread().getName() + " 使用管道：" + connection.hashCode());
    }

}
