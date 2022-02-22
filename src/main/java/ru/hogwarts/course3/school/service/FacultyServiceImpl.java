package ru.hogwarts.course3.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.course3.school.model.Faculty;
import ru.hogwarts.course3.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        if (facultyRepository.existsById(id)) {
            return facultyRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }


    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findFacultiesByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Collection <Faculty> findFacultiesByName (String name) {
        return facultyRepository.findFacultiesByName(name);
    }

}
