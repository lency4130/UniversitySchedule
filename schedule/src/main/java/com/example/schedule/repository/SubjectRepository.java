package com.example.schedule.repository;

import com.example.schedule.model.Subject;
import com.example.schedule.model.Teacher;
import com.example.schedule.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}