package com.example.schedule.controller;

import com.example.schedule.model.Student;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.model.Teacher;
import com.example.schedule.repository.TeacherRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	
	private final TeacherRepository repository;
	private final PasswordEncoder passwordEncoder;

    public TeacherController(TeacherRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping
    public List<Teacher> getSlots() {
        return repository.findAll();
    }
	
    
    @PostMapping("/create")
    public ResponseEntity<Teacher> createStudent(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password) {
    	
    	System.out.println("Received request to create teacher:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);

        String encodedPassword = passwordEncoder.encode(password);
        
        Teacher teacher = new Teacher();
        teacher.setFullName(fullName);
        teacher.setEmail(email);
        //student.setPassword(password);
        teacher.setPassword(encodedPassword);


        Teacher savedTeacher = repository.save(teacher);

        return ResponseEntity.ok(savedTeacher);
    }
}
