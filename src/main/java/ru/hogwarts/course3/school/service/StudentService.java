package ru.hogwarts.course3.school.service;

import ru.hogwarts.course3.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> findStudentsByAge(Integer age);

    Collection<Student> findStudentsByAgeBetween(Integer minAge, Integer maxAge);

    int getStudentsCount();

    int getStudentsAvgAge();

    List<Student> getLastStudents(int count);

    Double getAvgAgeOfStudents();

    List<String> getStudentsNameStartsWithA();

    int quickSumOfSequence();
}
