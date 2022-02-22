package ru.hogwarts.course3.school.service;

import ru.hogwarts.course3.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> findStudentsByAge(int age);

    Collection<Student> findStudentByAgeBetween (Integer minAge, Integer maxAge);
}
