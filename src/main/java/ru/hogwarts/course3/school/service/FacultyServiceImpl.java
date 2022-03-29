package ru.hogwarts.course3.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.course3.school.model.Faculty;
import ru.hogwarts.course3.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Method for creating faculty was invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        logger.info("Method for finding faculty with id {} was invoked", id);
        if (facultyRepository.existsById(id)) {
            return facultyRepository.findById(id).get();
        }
        logger.warn("Faculty with id: {} not found", id);
        return null;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info ("Method for editing faculty was invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Method for deleting faculty with id {} was invoked", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Method for getting all faculties was invoked");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findFacultiesByNameOrColor(String name, String color) {
        logger.info("Method for finding faculty by name '{}' or color '{}' was invoked", name, color);
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Method for finding the longest faculty name was invoked");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}

