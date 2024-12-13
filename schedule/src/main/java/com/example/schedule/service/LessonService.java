package com.example.schedule.service;

import com.example.schedule.model.Grade;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.Student;
import com.example.schedule.repository.GradeRepository;
import com.example.schedule.repository.LessonRepository;
import org.springframework.stereotype.Service;
import com.example.schedule.dto.LessonDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GradeRepository gradeRepository;

    public LessonService(LessonRepository lessonRepository, GradeRepository gradeRepository) {
        this.lessonRepository = lessonRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
    	lessonRepository.deleteById(id);
    }
    

    private LessonDTO convertToDTO(Lesson lesson, Grade grade) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setSubjectName(lesson.getSubject().getName());
        dto.setTeacherName(lesson.getTeacher().getFullName());
        dto.setTeacherEmail(lesson.getTeacher().getEmail());
        dto.setGroupName(lesson.getStudentGroup().getGroupName());
        dto.setTimeSlot(lesson.getTimeSlot().getTimeLabel());
        dto.setLessonDate(lesson.getLessonDate());

        if (grade != null) {
            dto.setGrade(grade.getGrade());
            dto.setComment(grade.getComment());
        }

        return dto;
    }
    
    public List<LessonDTO> getLessonsWithGradesForStudent(Student student) {
        List<Grade> grades = gradeRepository.findByStudent(student);
        List<Lesson> lessons = lessonRepository.findAll(); // Или фильтруйте по группе, если нужно

        return lessons.stream()
                .map(lesson -> {
                    Grade grade = grades.stream()
                            .filter(g -> g.getLesson().equals(lesson))
                            .findFirst()
                            .orElse(null);
                    return convertToDTO(lesson, grade);
                })
                .collect(Collectors.toList());
    }

    
}
