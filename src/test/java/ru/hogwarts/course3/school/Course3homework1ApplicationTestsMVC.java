package ru.hogwarts.course3.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.course3.school.controller.FacultyController;
import ru.hogwarts.course3.school.model.Faculty;
import ru.hogwarts.course3.school.repository.FacultyRepository;
import ru.hogwarts.course3.school.service.FacultyServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.course3.school.dataTest.*;

@WebMvcTest(controllers = FacultyController.class)
public class Course3homework1ApplicationTestsMVC {

    private Faculty faculty;
    private JSONObject facultyObject1;
    private JSONObject facultyObject2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    public void setUp() throws Exception {

        facultyObject1 = new JSONObject();
        facultyObject1.put("name", FACULTY_NAME_1);
        facultyObject1.put("color", FACULTY_COLOR_1);

        faculty = new Faculty();
        faculty.setId(FACULTY_ID_1);
        faculty.setName(FACULTY_NAME_1);
        faculty.setColor(FACULTY_COLOR_1);
    }

    @Test
    public void createFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(FACULTY_ID_1))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_1));
    }

    @Test
    public void createFacultyIfIdNotNullTest() throws Exception {
        when(facultyRepository.save(new Faculty(FACULTY_ID_1, FACULTY_NAME_1, FACULTY_COLOR_1, null))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(new Faculty(FACULTY_ID_1, FACULTY_NAME_1, FACULTY_COLOR_1, null).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findFacultyByIdTest() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + FACULTY_ID_1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID_1))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_1));
    }

    @Test
    public void findFacultyByIdIfNotFoundTest() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + FACULTY_ID_5)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editFacultyTest() throws Exception {
        facultyObject2 = new JSONObject();
        facultyObject2.put("id", FACULTY_ID_1);
        facultyObject2.put("name", FACULTY_NAME_2);
        facultyObject2.put("color", FACULTY_COLOR_2);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(new Faculty(FACULTY_ID_1, FACULTY_NAME_2, FACULTY_COLOR_2, null));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID_1))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME_2))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR_2));
    }

    @Test
    public void editFacultyIfNotFoundTest() throws Exception {
        facultyObject2 = new JSONObject();
        facultyObject2.put("id", FACULTY_ID_5);
        facultyObject2.put("name", FACULTY_NAME_2);
        facultyObject2.put("color", FACULTY_COLOR_2);

        when(facultyRepository.save(any(Faculty.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        doNothing().when(facultyRepository).deleteById(faculty.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + FACULTY_ID_1))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFacultyIfNotFoundTest() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + FACULTY_ID_5))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllFaculties() throws Exception {
        List<Faculty> facultyList = Arrays.asList(
                new Faculty(FACULTY_ID_1, FACULTY_NAME_1, FACULTY_COLOR_1, null),
                new Faculty(FACULTY_ID_2, FACULTY_NAME_2, FACULTY_COLOR_2, null));

        when(facultyRepository.findAll()).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(FACULTY_ID_1))
                .andExpect(jsonPath("$[1].id").value(FACULTY_ID_2))
                .andExpect(jsonPath("$[0].name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$[1].name").value(FACULTY_NAME_2));
    }

    @Test
    public void findAllFacultiesByName() throws Exception {
        List<Faculty> facultyList = Arrays.asList(
                new Faculty(FACULTY_ID_1, FACULTY_NAME_1, FACULTY_COLOR_1, null),
                new Faculty(FACULTY_ID_3, FACULTY_NAME_3, FACULTY_COLOR_3, null));

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any())).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=" + FACULTY_NAME_1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(FACULTY_ID_1))
                .andExpect(jsonPath("$[1].id").value(FACULTY_ID_3))
                .andExpect(jsonPath("$[0].name").value(FACULTY_NAME_1))
                .andExpect(jsonPath("$[1].name").value(FACULTY_NAME_3));
    }

    @Test
    public void findAllFacultiesByColor() throws Exception {
        List<Faculty> facultyList = Arrays.asList(
                new Faculty(FACULTY_ID_2, FACULTY_NAME_2, FACULTY_COLOR_2, null),
                new Faculty(FACULTY_ID_3, FACULTY_NAME_3, FACULTY_COLOR_3, null));

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(
                any(), any(String.class)))
                .thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=" + FACULTY_COLOR_2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(FACULTY_ID_2))
                .andExpect(jsonPath("$[1].id").value(FACULTY_ID_3))
                .andExpect(jsonPath("$[0].name").value(FACULTY_NAME_2))
                .andExpect(jsonPath("$[1].name").value(FACULTY_NAME_3));
    }

    @Test
    public void findAllFacultiesIfListIsEmpty() throws Exception {
        List<Faculty> facultyList = Collections.emptyList();

        when(facultyRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
