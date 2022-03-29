package ru.hogwarts.course3.school.service;

import ru.hogwarts.course3.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(Long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(Long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findFacultiesByNameOrColor(String name, String color);

    String getLongestFacultyName();
}
