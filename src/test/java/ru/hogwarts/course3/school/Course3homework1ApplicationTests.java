package ru.hogwarts.course3.school;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.course3.school.controller.StudentController;
import ru.hogwarts.course3.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Course3homework1ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String name = "Alice";
    private final Long id = 1L;
    private final int age = 16;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void postStudentTest() throws Exception {
        Student student = createTestStudent();

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody().getId());
        assertEquals(response.getBody().getName(), name);
        assertEquals(response.getBody().getAge(), age);

    }

    //неверно
    @Test
    public void postStudentTestIfIdIsNotNull() throws Exception {
        Student student = createTestStudent();
        student.setId(id);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getStudentByIdTest() throws Exception {
//        Student student = createTestStudent();
//        long id = student.getId();
//
//        ResponseEntity <Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/{id}", Student.class, id);
//
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertEquals(getStudent.getName(), name);
//        assertEquals(response.getBody().getAge(), age);
    }

    @Test
    public void getStudentIfItNotExist() throws Exception {

        ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/{id}", Student.class, 2L);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student student = createTestStudent();
        student.setId(id);
      long id = student.getId();

        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student/{id}", HttpMethod.DELETE, null, Student.class, id);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    private Student createTestStudent() {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }
}

