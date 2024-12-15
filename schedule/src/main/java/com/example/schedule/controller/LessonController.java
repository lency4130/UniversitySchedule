package com.example.schedule.controller;

import com.example.schedule.model.Lesson;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.model.Teacher;
import com.example.schedule.service.LessonService;
import com.example.schedule.service.TeacherService;
import com.example.schedule.service.StudentGroupService;
import com.example.schedule.dto.LessonDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.schedule.service.StudentService;
import com.example.schedule.model.Student;
import com.example.schedule.model.StudentGroup;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final StudentGroupService studentGroupService;

    public LessonController(LessonService lessonService, StudentService studentService, TeacherService teacherService, StudentGroupService studentGroupService) {
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.studentGroupService = studentGroupService; 
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
    
    @GetMapping("/by-group")
    public List<Lesson> getLessonsByGroup(@RequestParam Long groupId) {
        // Получаем группу через Optional
        StudentGroup studentGroup = studentGroupService.getStudentGroupById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

        // Возвращаем уроки для группы
        return lessonService.getLessonsByGroup(studentGroup);
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
    
    @PostMapping("/add-lesson")
    public ResponseEntity<Lesson> addLesson(
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            @RequestParam Long groupId,
            @RequestParam Long timeSlotId,
            @RequestParam String lessonDate) {
        try {
        	
        	System.out.println("Subject ID: " + subjectId);
            System.out.println("Teacher ID: " + teacherId);
            System.out.println("Group ID: " + groupId);
            System.out.println("Time Slot ID: " + timeSlotId);
            System.out.println("Lesson Date: " + lessonDate);
        	
            // Парсинг даты
            LocalDate parsedDate = LocalDate.parse(lessonDate);

            // Вызов метода addLesson
            Lesson newLesson = lessonService.addLesson(subjectId, teacherId, groupId, timeSlotId, parsedDate);
            return new ResponseEntity<>(newLesson, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Если связанная сущность не найдена
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
