package com.example.schedule.service;

import com.example.schedule.model.Teacher;
import com.example.schedule.repository.TeacherRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;



@Service
public class TeacherService {
	
	private final TeacherRepository teacherRepository;
	
	public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
	
	public Teacher findTeacherByEmail(String email) {
	    return teacherRepository.findByEmail(email);
	}
	
	public Optional<Teacher> findTeacherById(Long id) {
		return teacherRepository.findById(id);
	}
}
