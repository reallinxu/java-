package com.reallinxu.conn;

public interface IMyPool {

    MyPooledConnection getMyPooledConnection();

    void createMyPooledConnection(int count);
}
