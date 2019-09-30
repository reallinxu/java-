package com.reallinxu.conn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyPooledConnection {

    private Connection connection;

    //通道关闭标志
    private Boolean isBusy;

    MyPooledConnection(Connection connection, Boolean isBusy) {
        this.connection = connection;
        this.isBusy = isBusy;
    }

    public void close() {
        this.isBusy = false;
    }

    public ResultSet query(String sql) {
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Boolean getBusy() {
        return isBusy;
    }

    public void setBusy(Boolean busy) {
        isBusy = busy;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
