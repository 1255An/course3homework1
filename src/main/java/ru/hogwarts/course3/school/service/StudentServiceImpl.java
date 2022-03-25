package ru.hogwarts.course3.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.course3.school.model.Student;
import ru.hogwarts.course3.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Method for creating student was invoked");
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Method for finding student was invoked");
        logger.debug("Request to find student with id: {} ", id);
        if (studentRepository.existsById(id)) {
            return studentRepository.findById(id).get();
        } else {
            logger.warn("Student with id: {} not found", id);
            return null;
        }
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Method for editing student was invoked");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Method for deleting student was invoked");
        logger.debug("Request to delete student with id: {} ", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Method for getting all students was invoked");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findStudentsByAge(Integer age) {
        logger.info("Method for finding students by age was invoked");
        logger.debug("Request to find students with age: {} ", age);
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Student> findStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Method for finding students by age between was invoked");
        logger.debug("Request to find students with age between {} and {}", minAge, maxAge);
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    @Override
    public int getStudentsCount() {
        logger.info("Method for counting students was invoked");
        return studentRepository.getStudentsCount();
    }

    @Override
    public int getStudentsAvgAge() {
        logger.info("Method for counting average amount of students was invoked");
        return studentRepository.getStudentsAvgAge();
    }

    @Override
    public List<Student> getLastStudents(int count) {
        logger.info("Method for getting last " + count + " students was invoked");
        logger.debug("Request to get last {} students", count);
        return studentRepository.getLastStudents(count);
    }

    @Override
    public List<String> getStudentsNameStartsWithA() {
        List<String> names = studentRepository.findAll().stream()
                .filter(s -> s.getName().toUpperCase().startsWith("A"))
                .map(s -> s.getName().toUpperCase())
                .sorted(Comparator.comparing(String::valueOf))
                .collect(Collectors.toList());
        return names;
    }

    @Override
    public Double getAvgAgeOfStudents() {
        return studentRepository.findAll().stream()
                .collect(Collectors.averagingInt(Student::getAge));
    }

    @Override
    public int quickSumOfSequence() {
        int result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        return result;
    }
}



