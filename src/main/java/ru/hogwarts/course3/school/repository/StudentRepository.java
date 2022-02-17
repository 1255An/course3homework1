package ru.hogwarts.course3.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.course3.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
