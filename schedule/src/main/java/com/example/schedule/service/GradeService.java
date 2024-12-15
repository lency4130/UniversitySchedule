package com.example.schedule.service;


import com.example.schedule.model.Grade;
import com.example.schedule.dto.GradeDTO;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.Student;
import com.example.schedule.repository.GradeRepository;
import com.example.schedule.repository.LessonRepository;
import com.example.schedule.repository.StudentRepository;
import org.springframework.stereotype.Service;
import com.example.schedule.dto.LessonDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    
    

    public GradeService(GradeRepository gradeRepository, LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    /*
    public Grade addGrade(Long lessonId, Long studentId, int gradeValue, String comment) {
    	
    	 System.out.println("Received lessonId: " + lessonId);
    	    System.out.println("Received studentId: " + studentId);
    	    System.out.println("Received grade: " + gradeValue);
    	    System.out.println("Received comment: " + comment);
    	
    	System.out.println("Fetching lesson with ID: " + lessonId);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        
        System.out.println("Fetching student with ID: " + studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        System.out.println("Checking student group...");
        if (!lesson.getStudentGroup().equals(student.getStudentGroup())) {
            throw new RuntimeException("Student does not belong to the group for this lesson");
        }
        
        System.out.println("Checking existing grade...");
        Optional<Grade> existingGrade = gradeRepository.findByLessonAndStudent(lesson, student);
        if (existingGrade.isPresent()) {
            throw new RuntimeException("Grade already exists for this lesson and student");
        }

        System.out.println("Saving grade...");
        Grade grade = new Grade();
        grade.setLesson(lesson);
        grade.setStudent(student);
        grade.setGrade(gradeValue);
        grade.setComment(comment);
        
        

        return gradeRepository.save(grade);
    }
    */
    
    public Grade addGrade(Long lessonId, Long studentId, int gradeValue, String comment) {
        System.out.println("Received lessonId: " + lessonId);
        System.out.println("Received studentId: " + studentId);
        System.out.println("Received grade: " + gradeValue);
        System.out.println("Received comment: " + comment);

        System.out.println("Fetching lesson with ID: " + lessonId);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        System.out.println("Fetching student with ID: " + studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        System.out.println("Checking student group...");
        if (!lesson.getStudentGroup().equals(student.getStudentGroup())) {
            throw new RuntimeException("Student does not belong to the group for this lesson");
        }

        System.out.println("Checking existing grade...");
        Optional<Grade> existingGrade = gradeRepository.findByLessonAndStudent(lesson, student);

        Grade grade;
        if (existingGrade.isPresent()) {
            System.out.println("Grade exists, updating...");
            grade = existingGrade.get();
            grade.setGrade(gradeValue);
            grade.setComment(comment);
        } else {
            System.out.println("Creating new grade...");
            grade = new Grade();
            grade.setLesson(lesson);
            grade.setStudent(student);
            grade.setGrade(gradeValue);
            grade.setComment(comment);
        }

        return gradeRepository.save(grade);
    }

    
    
    public List<GradeDTO> getGradesByLessonId(Long lessonId) {
        List<Grade> grades = gradeRepository.findByLessonId(lessonId);

        return grades.stream()
            .map(grade -> new GradeDTO(
                grade.getId(),
                grade.getStudent().getId(), // Передаём studentId
                grade.getStudent().getFullName(),
                grade.getGrade(),
                grade.getComment()
            ))
            .collect(Collectors.toList());
    }
}
