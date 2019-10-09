package com.reallinxu;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SingleThreadConnectionHolder {

    private static ThreadLocal<ConnectionHolder> threadLocal = new ThreadLocal<ConnectionHolder>();

    private static ConnectionHolder getConnectionHolder() {
        ConnectionHolder connectionHolder = threadLocal.get();

        if (connectionHolder == null) {
            connectionHolder = new ConnectionHolder();
            threadLocal.set(connectionHolder);
        }
        return connectionHolder;
    }

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        return getConnectionHolder().getConnectionByDataSource(dataSource);
    }

}
