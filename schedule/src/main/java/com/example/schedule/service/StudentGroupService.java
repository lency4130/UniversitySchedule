package com.example.schedule.service;

import com.example.schedule.model.Student;
import com.example.schedule.model.StudentGroup;

import com.example.schedule.repository.StudentGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentGroupService {
	
    private final StudentGroupRepository studentGroupRepository;


    public StudentGroupService( 
                         StudentGroupRepository studentGroupRepository) 
    {

        this.studentGroupRepository = studentGroupRepository;
    }
	
	public List<StudentGroup> getAllGroups() {
        return studentGroupRepository.findAll();
    }
	
	public Optional<StudentGroup> getStudentGroupById(Long id) {
        return studentGroupRepository.findById(id);
    }

}
