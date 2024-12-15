package com.example.schedule.service;

import com.example.schedule.model.Teacher;
import com.example.schedule.repository.TeacherRepository;
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
}
