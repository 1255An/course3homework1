package ru.hogwarts.course3.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class Course3homework1ApplicationTestMVC {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
}
