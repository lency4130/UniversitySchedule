package com.example.schedule.controller;

import com.example.schedule.model.Student;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.service.StudentGroupService;
import com.example.schedule.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
    private final PasswordEncoder passwordEncoder;
    private final StudentGroupService studentGroupService;

    public StudentController(StudentService service, StudentGroupService studentGroupService, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.studentGroupService = studentGroupService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return service.getStudentById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }
    
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam Long groupId,
            @RequestParam String password) {
    	
    	System.out.println("Received request to create student:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Group ID: " + groupId);
        // Проверяем существование группы
        StudentGroup studentGroup = studentGroupService.getStudentGroupById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        
        String encodedPassword = passwordEncoder.encode(password);

        // Создаем нового студента
        Student student = new Student();
        student.setFullName(fullName);
        student.setEmail(email);
        student.setStudentGroup(studentGroup);
        //student.setPassword(password);
        student.setPassword(encodedPassword);

        // Сохраняем студента
        Student savedStudent = service.saveStudent(student);

        return ResponseEntity.ok(savedStudent);
    }
    
}
