package com.example.schedule.repository;

import com.example.schedule.model.Grade;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(Student student);
    Optional<Grade> findByLessonAndStudent(Lesson lesson, Student student);
    List<Grade> findByLessonId(Long lessonId);
}
