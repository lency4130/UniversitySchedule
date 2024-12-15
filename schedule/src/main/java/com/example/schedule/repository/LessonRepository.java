package com.example.schedule.repository;

import com.example.schedule.model.Lesson;
import com.example.schedule.model.Teacher;
import com.example.schedule.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	List<Lesson> findByStudentGroup(StudentGroup studentGroup);
	List<Lesson> findByTeacher(Teacher teacher);
}
