package ru.hogwarts.course3.school.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.course3.school.model.Student;
import ru.hogwarts.course3.school.service.StudentService;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }


    @GetMapping("/{id}")
    public ResponseEntity <Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        if (student.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Student foundStudent = studentService.editStudent(student);
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        if (studentService.findStudent(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByAge/{age}")
    public ResponseEntity<Collection<Student>> findStudentsByAge(@PathVariable Integer age) {
        return ResponseEntity.ok(studentService.findStudentsByAge(age));
    }
}
