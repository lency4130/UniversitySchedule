package com.example.schedule.repository;

import com.example.schedule.model.Student;
import java.util.List;
import com.example.schedule.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    List<Student> findAllByStudentGroup(StudentGroup studentGroup);
}
