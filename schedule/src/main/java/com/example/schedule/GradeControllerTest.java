package com.example.schedule;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // Импорт метода post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Импорт метода status

import com.example.schedule.repository.GradeRepository;
import com.example.schedule.model.Grade;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GradeRepository gradeRepository; // Подключаем репозиторий

    @Test
    public void testAddGrade() throws Exception {
        mockMvc.perform(post("/api/grades")
                .param("lessonId", "1")
                .param("studentId", "3")
                .param("grade", "5")
                .param("comment", "Good job"))
                .andExpect(status().isOk());

        // Проверяем, что запись добавлена в базу данных
        Grade grade = gradeRepository.findById(1L).orElse(null); // Замените ID на нужное значение
        assertNotNull(grade); // Проверяем, что запись существует
        assertEquals(5, grade.getGrade());
        assertEquals("Good job", grade.getComment());
    }
}

