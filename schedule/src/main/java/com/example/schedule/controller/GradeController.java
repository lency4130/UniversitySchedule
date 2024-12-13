package com.example.schedule.controller;

import com.example.schedule.service.GradeService;
import com.example.schedule.model.Grade;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public Grade addGrade(@RequestParam Long lessonId,
                          @RequestParam Long studentId,
                          @RequestParam int grade,
                          @RequestParam(required = false) String comment) {
        // return gradeService.addGrade(lessonId, studentId, grade, comment);
    	try {
            return gradeService.addGrade(lessonId, studentId, grade, comment);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    @GetMapping
    public String test() {
        return "Route is working";
    }

}
