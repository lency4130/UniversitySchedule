package com.example.schedule.service;

import com.example.schedule.dto.LessonDTO;
import com.example.schedule.model.Lesson;
import com.example.schedule.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository repository;

    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public List<Lesson> getAllLessons() {
        return repository.findAll();
    }

    public Optional<Lesson> getLessonById(Long id) {
        return repository.findById(id);
    }

    public Lesson saveLesson(Lesson lesson) {
        return repository.save(lesson);
    }

    public void deleteLesson(Long id) {
        repository.deleteById(id);
    }
    

    private LessonDTO convertToDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setSubjectName(lesson.getSubject().getName());
        dto.setTeacherName(lesson.getTeacher().getFullName());
        dto.setTeacherEmail(lesson.getTeacher().getEmail());
        dto.setGroupName(lesson.getStudentGroup().getGroupName());
        dto.setTimeSlot(lesson.getTimeSlot().getTimeLabel());
        dto.setLessonDate(lesson.getLessonDate());
        return dto;
    }
    
}
