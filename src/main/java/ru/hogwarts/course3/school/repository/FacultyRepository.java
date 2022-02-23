package ru.hogwarts.course3.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.course3.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Faculty> findFacultiesByColorIgnoreCase(String color);
}
