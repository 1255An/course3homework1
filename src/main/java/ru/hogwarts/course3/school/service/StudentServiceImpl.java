package ru.hogwarts.course3.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.course3.school.model.Faculty;
import ru.hogwarts.course3.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;


    @Override
    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    @Override
    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudent(long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    @Override
    public Collection<Student> getAllStudents() {
        return students.values();
    }

    @Override
    public Collection<Student> findStudentsByAge(int age) {
        return getAllStudents().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

}
