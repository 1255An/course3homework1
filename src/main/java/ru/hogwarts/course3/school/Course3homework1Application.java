package ru.hogwarts.course3.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition
public class Course3homework1Application {

    public static void main(String[] args) {
        SpringApplication.run(Course3homework1Application.class, args);
    }

}
