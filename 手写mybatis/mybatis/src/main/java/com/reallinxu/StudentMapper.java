package com.reallinxu;

public interface StudentMapper {

    Student findById(int id);

    void insertStudent(Student student);
}
