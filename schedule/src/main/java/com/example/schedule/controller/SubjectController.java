package com.example.schedule.controller;

import com.example.schedule.model.Subject;
import com.example.schedule.model.Teacher;
import com.example.schedule.repository.SubjectRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	
	private final SubjectRepository repository;


    public SubjectController(SubjectRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping
    public List<Subject> getSlots() {
        return repository.findAll();
    }
    
    @PostMapping("/create")
    public ResponseEntity<Subject> createStudent(
            @RequestParam String name) {
    	
    	System.out.println("Received request to create subject:");
        System.out.println("name: " + name);


        Subject subject = new Subject();
        subject.setName(name);



        Subject savedSubject = repository.save(subject);

        return ResponseEntity.ok(savedSubject);
    }
	
}
