package ru.hogwarts.course3.school;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import ru.hogwarts.course3.school.controller.StudentController;
import ru.hogwarts.course3.school.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Course3homework1ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String name = "Alice";
    private final Long id = 100L;
    private final int age = 16;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void postStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Alice");
        student.setAge(16);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(age, response.getBody().getAge());

    }


    @Test
    public void postStudentTestIfIdIsNotNull() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Alice");
        student.setAge(16);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql("/test.sql")
    public void getStudentByIdTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port +
                "/student/100", Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(age, response.getBody().getAge());
    }

    @Test
    public void getStudentIfNotExist() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port +
                "/student/5", Student.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @Sql("/test.sql")
    public void editStudentTest() throws Exception {
        Student student = new Student();
        student.setId(100L);
        student.setName("Joe");
        student.setAge(20);

        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student", HttpMethod.PUT, new HttpEntity<>(student), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("Joe", response.getBody().getName());
        assertEquals(20, response.getBody().getAge());
    }

    @Test
    @Sql("/test.sql")
    public void deleteStudentTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student/100", HttpMethod.DELETE, null, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @Sql("/test.sql")
    public void deleteStudentIfNotExistTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student/102", HttpMethod.DELETE, null, Student.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql("/test.sql")
    public void findStudentsByAge() throws Exception {
        ResponseEntity<List<Student>> response = this.restTemplate.exchange("http://localhost:" + port +
                        "/student?age=10", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = response.getBody();
        assertEquals(1, students.size());
        assertEquals("Tom", students.get(0).getName());
    }

    @Test
    @Sql("/test.sql")
    public void findStudentsByAgeBetween() throws Exception {
        ResponseEntity<List<Student>> response = this.restTemplate.exchange("http://localhost:" + port +
                        "/student?minAge=15&maxAge=20", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = response.getBody();
        assertEquals(2, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Brad", students.get(1).getName());
    }

    @Test
    @Sql("/test.sql")
    public void getAllStudents() throws Exception {
        ResponseEntity<List<Student>> response = this.restTemplate.exchange("http://localhost:" + port +
                        "/student", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = response.getBody();
        assertEquals(3, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Tom", students.get(1).getName());
        assertEquals("Brad", students.get(2).getName());
    }
}

