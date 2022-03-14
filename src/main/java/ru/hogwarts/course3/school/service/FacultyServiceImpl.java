package ru.hogwarts.course3.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.course3.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        return null;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> findFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

}
