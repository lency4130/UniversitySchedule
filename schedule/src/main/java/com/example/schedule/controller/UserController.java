package com.example.schedule.controller;

import com.example.schedule.model.Student;
import com.example.schedule.model.Teacher;
import com.example.schedule.model.Admin;
import com.example.schedule.repository.StudentRepository;
import com.example.schedule.repository.TeacherRepository;
import com.example.schedule.repository.AdminRepository;
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
    private final AdminRepository adminRepository;

    public UserController(StudentRepository studentRepository, TeacherRepository teacherRepository, AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.adminRepository = adminRepository;
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

        // Проверяем, студент это, учитель или админ
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            return Map.of(
                "role", "STUDENT",
                "fullName", student.getFullName(),
                "group", student.getStudentGroup().getGroupName() 
            );
        }

        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher != null) {
            return Map.of(
                "role", "TEACHER",
                "fullName", teacher.getFullName()
            );
        }
        
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return Map.of(
                "role", "ADMIN",
                "fullName", admin.getFullName()
            );
        }

        return Map.of("message", "User not found");
    }
}
