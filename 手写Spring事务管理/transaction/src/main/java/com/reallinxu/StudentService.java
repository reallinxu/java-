package com.reallinxu;

import javax.sql.DataSource;
import java.sql.SQLException;

public class StudentService {

    private StudentDao studentDao;
    private TransactionManager transactionManager;

    public StudentService(DataSource dataSource) {
        studentDao = new StudentDao(dataSource);
        transactionManager = new TransactionManager(dataSource);
    }

    public void action() {
        try {
            transactionManager.start();
            studentDao.insert();
            studentDao.update();
            transactionManager.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            transactionManager.rollback();
        }
    }


}
