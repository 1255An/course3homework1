package ru.hogwarts.course3.school.service;

import ru.hogwarts.course3.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student findStudent(long id);

    Student editStudent(Student student);

    Student deleteStudent(long id);

    Collection<Student> getAllStudents();

    Collection<Student> findStudentsByAge(int age);
}
