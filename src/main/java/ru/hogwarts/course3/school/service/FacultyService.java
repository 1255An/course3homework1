package ru.hogwarts.course3.school.service;

import ru.hogwarts.course3.school.model.Faculty;

import java.util.Collection;
import java.util.Collections;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findFacultiesByColor(String color);
}
