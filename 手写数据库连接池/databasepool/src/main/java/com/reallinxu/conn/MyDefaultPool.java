package com.reallinxu.conn;

import com.reallinxu.config.DBConfigXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class MyDefaultPool implements IMyPool {

    private Vector<MyPooledConnection> myPooledConnectionVector = new Vector<MyPooledConnection>();
    private static String jdbcURL;
    private static String jdbcUserName;
    private static String jdbcPassword;
    private static int initCount;
    private static int step;
    private static int maxCount;

    public MyDefaultPool() {
        init();
        createMyPooledConnection(initCount);
    }

    private void init() {
        jdbcURL = DBConfigXML.jdbcURL;
        jdbcUserName = DBConfigXML.jdbcUserName;
        jdbcPassword = DBConfigXML.jdbcPassWord;
        initCount = DBConfigXML.initCount;
        step = DBConfigXML.step;
        maxCount = DBConfigXML.maxCount;
    }

    @Override
    public MyPooledConnection getMyPooledConnection() {
        if (myPooledConnectionVector.size() < 1) {
            throw new RuntimeException("连接池初始化失败");
        }

        try {
            MyPooledConnection myPooledConnection = getRealConnectionFromPool();
            if (myPooledConnection == null) {
                createMyPooledConnection(step);
                myPooledConnection = getRealConnectionFromPool();
            }
            return myPooledConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createMyPooledConnection(int count) {
        if (myPooledConnectionVector.size() > maxCount || (myPooledConnectionVector.size()) + count > maxCount) {
            throw new RuntimeException("连接池已满!");
        }

        for (int i = 0; i < count; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MyPooledConnection myPooledConnection = new MyPooledConnection(connection, false);
            myPooledConnectionVector.add(myPooledConnection);
        }
    }

    private synchronized MyPooledConnection getRealConnectionFromPool() throws SQLException {
        for (MyPooledConnection myPooledConnection : myPooledConnectionVector) {

            if (!myPooledConnection.getBusy()) {
                if (myPooledConnection.getConnection().isValid(3000)) {
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                } else {
                    Connection connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
                    myPooledConnection.setConnection(connection);
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                }
            }
        }
        return null;
    }

}
