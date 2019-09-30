package com.reallinxu.Test;

import com.reallinxu.conn.IMyPool;
import com.reallinxu.conn.MyPoolFactory;
import com.reallinxu.conn.MyPooledConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {


    public static void main(String[] args) throws SQLException {
        IMyPool iMyPool = MyPoolFactory.getInstance();

        for (int i = 0; i < 1000; i++) {
            MyPooledConnection myPooledConnection = iMyPool.getMyPooledConnection();
            ResultSet query = myPooledConnection.query("select * from student");

            while(query.next()){
                System.out.println(myPooledConnection.getConnection() + "-" + query.getString("age") + "-" + query.getString("name"));
            }
            //此处此时连接池满的情况
//            myPooledConnection.close();
        }
    }
}
