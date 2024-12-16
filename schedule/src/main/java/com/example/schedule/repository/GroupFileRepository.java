package com.example.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schedule.model.GroupFile;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.StudentGroup;

public interface GroupFileRepository extends JpaRepository<GroupFile, Long> {
	List<GroupFile> findByStudentGroup(StudentGroup studentGroup);
}

