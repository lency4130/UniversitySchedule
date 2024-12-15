package com.example.schedule.controller;

import com.example.schedule.model.Lesson;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.repository.StudentGroupRepository;
import com.example.schedule.model.Subject;
import com.example.schedule.model.Teacher;
import com.example.schedule.service.StudentGroupService;
import com.example.schedule.service.TeacherService;
import com.example.schedule.dto.LessonDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.schedule.service.StudentService;
import com.example.schedule.model.Student;


@RestController
@RequestMapping("/api/student-group")
public class StudentGroupController {
	
	private final StudentGroupService service;
	private final StudentGroupRepository studentGroupRepository;

    public StudentGroupController(StudentGroupService service, StudentGroupRepository studentGroupRepository) {
        this.service = service;
        this.studentGroupRepository = studentGroupRepository; 
    }

    @GetMapping
    public List<StudentGroup> getGroups() {
        return service.getAllGroups();
    }
    
    @PostMapping("/create")
    public ResponseEntity<StudentGroup> createStudent(
            @RequestParam String name) {
    	
    	System.out.println("Received request to create subject:");
        System.out.println("name: " + name);


        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName(name);



        StudentGroup savedstudentGroup = studentGroupRepository.save(studentGroup);

        return ResponseEntity.ok(savedstudentGroup);
    }
}
