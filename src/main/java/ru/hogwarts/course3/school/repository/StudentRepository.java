package ru.hogwarts.course3.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.course3.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT (*) FROM student", nativeQuery = true)
    int getStudentsCount();

    @Query(value = "SELECT AVG (*) FROM student", nativeQuery = true)
    int getStudentsAvgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Student> getLastStudents(int count);

}
