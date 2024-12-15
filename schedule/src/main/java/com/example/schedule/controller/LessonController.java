package com.example.schedule.controller;

import com.example.schedule.model.Lesson;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.model.Teacher;
import com.example.schedule.service.LessonService;
import com.example.schedule.service.TeacherService;
import com.example.schedule.dto.LessonDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.schedule.service.StudentService;
import com.example.schedule.model.Student;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public LessonController(LessonService lessonService, StudentService studentService, TeacherService teacherService) {
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }
    
    @GetMapping("/my-group")
    public List<Lesson> getScheduleForStudentGroup() {
        // Получаем текущего авторизованного студента
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Student student = studentService.findStudentByEmail(email);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Получаем расписание по группе студента
        return lessonService.getLessonsByGroup(student.getStudentGroup());
    }

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonService.saveLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
    	lessonService.deleteLesson(id);
    }
    
    @GetMapping("/with-grades")
    public List<LessonDTO> getLessonsWithGrades() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Student student = studentService.findStudentByEmail(email);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        return lessonService.getLessonsWithGradesForStudent(student, student.getStudentGroup());
    }
    
    // получить уроки учителя
    @GetMapping("/my-lessons")
    public List<Lesson> getLessonsForTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Teacher teacher = teacherService.findTeacherByEmail(email);

        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        return lessonService.getLessonsByTeacher(teacher);
    }
    
    @GetMapping("/{lessonId}/students")
    public List<Student> getStudentsForLesson(@PathVariable Long lessonId) {
        // Получаем урок по ID
        Lesson lesson = lessonService.getLessonById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        // Получаем группу, связанную с уроком
        StudentGroup group = lesson.getStudentGroup();

        // Возвращаем студентов из группы
        return studentService.getStudentsByGroup(group);
    }
}
