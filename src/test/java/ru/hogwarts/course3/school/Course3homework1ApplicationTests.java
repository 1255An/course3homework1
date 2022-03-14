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
import static ru.hogwarts.course3.school.dataTest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class Course3homework1ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student(STUDENT_NAME_1, STUDENT_AGE_1, null);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(STUDENT_NAME_1, response.getBody().getName());
        assertEquals(STUDENT_AGE_1, response.getBody().getAge());

    }

    @Test
    public void createStudentTestIfIdIsNotNull() throws Exception {
        Student student = new Student(STUDENT_ID_1, STUDENT_NAME_1, STUDENT_AGE_1, null);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "/student", student, Student.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql("/test.sql")
    public void getStudentByIdTest() throws Exception {

        ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port +
                "/student/{id}", Student.class, STUDENT_ID_1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Phoebe", response.getBody().getName());
        assertEquals(26, response.getBody().getAge());
    }

    @Test
    public void getStudentIfNotExist() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port +
                "/student/{id}", Student.class, STUDENT_ID_5);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql("/test.sql")
    public void editStudentTest() throws Exception {
        Student student = new Student(STUDENT_ID_1, STUDENT_NAME_2, STUDENT_AGE_2, null);

        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student", HttpMethod.PUT, new HttpEntity<>(student), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(STUDENT_ID_1, response.getBody().getId());
        assertEquals(STUDENT_NAME_2, response.getBody().getName());
        assertEquals(STUDENT_AGE_2, response.getBody().getAge());
    }

    @Test
    @Sql("/test.sql")
    public void deleteStudentTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student/{id}", HttpMethod.DELETE, null, Student.class, STUDENT_ID_1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @Sql("/test.sql")
    public void deleteStudentIfNotExistTest() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port +
                "/student/{id}", HttpMethod.DELETE, null, Student.class, STUDENT_ID_5);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql("/test.sql")
    public void findStudentsByAge() throws Exception {
        ResponseEntity<List<Student>> response = this.restTemplate.exchange("http://localhost:" + port +
                        "/student?age=26", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = response.getBody();
        assertEquals(1, students.size());
        assertEquals("Phoebe", students.get(0).getName());
    }

    @Test
    @Sql("/test.sql")
    public void findStudentsByAgeBetween() throws Exception {
        ResponseEntity<List<Student>> response = this.restTemplate.exchange("http://localhost:" + port +
                        "/student?minAge=20&maxAge=29", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = response.getBody();
        assertEquals(2, students.size());
        assertEquals("Phoebe", students.get(0).getName());
        assertEquals("Janis", students.get(1).getName());
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
        assertEquals("Phoebe", students.get(0).getName());
        assertEquals("Ross", students.get(1).getName());
        assertEquals("Janis", students.get(2).getName());
    }

}

