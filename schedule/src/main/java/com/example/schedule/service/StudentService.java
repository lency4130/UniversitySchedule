package com.example.schedule.service;

import com.example.schedule.model.Student;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;
    
    public List<Student> getStudentsByGroup(StudentGroup group) {
        return repository.findAllByStudentGroup(group);
    }

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }
    
    public Student findStudentByEmail(String email) {
        return repository.findByEmail(email);
    }
}
