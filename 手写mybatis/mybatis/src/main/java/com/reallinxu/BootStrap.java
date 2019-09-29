package com.reallinxu;

public class BootStrap {
    public static void main(String[] args) {
        MySqlSession sqlSession = new MyDefautSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        int a = 23;
        Student student = mapper.findById(a);
        System.out.println(student.getAge());
        System.out.println(student.getName());
    }
}
