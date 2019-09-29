package com.reallinxu;

import java.sql.*;

public class MyBaseExecutor implements MyExecutor{
    public <T> T query(String statemenet) {
        //JDBC完成DB操作获取结果
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
            String sql = statemenet;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Student student = null;
            if(resultSet.next()){
                student = new Student();
                student.setAge(resultSet.getString("age"));
                student.setName(resultSet.getString("name"));
            }
            return (T) student;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
