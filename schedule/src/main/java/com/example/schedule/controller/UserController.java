package com.example.schedule.controller;

import com.example.schedule.model.Student;
import com.example.schedule.model.Teacher;
import com.example.schedule.repository.StudentRepository;
import com.example.schedule.repository.TeacherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public UserController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/current")
    public Map<String, Object> getCurrentUser() {
        // Получаем текущую аутентификацию
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return Map.of("message", "No user is currently authenticated");
        }

        // Email из principal
        String email = authentication.getName();

        // Проверяем, студент это или учитель
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            return Map.of(
                "role", "STUDENT",
                "fullName", student.getFullName(),
                "group", student.getStudentGroup().getGroupName() // Предполагается, что StudentGroup имеет поле name
            );
        }

        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher != null) {
            return Map.of(
                "role", "TEACHER",
                "fullName", teacher.getFullName()
            );
        }

        return Map.of("message", "User not found");
    }
}
