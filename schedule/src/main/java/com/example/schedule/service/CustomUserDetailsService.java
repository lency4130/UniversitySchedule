package com.example.schedule.service;

import com.example.schedule.model.Role;
import com.example.schedule.model.Student;
import com.example.schedule.model.Teacher;
import com.example.schedule.model.Admin;
import com.example.schedule.repository.StudentRepository;
import com.example.schedule.repository.TeacherRepository;
import com.example.schedule.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(StudentRepository studentRepository, TeacherRepository teacherRepository, AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .roles(student.getRole().name())
                    .build();
        }

        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher != null) {
            return User.builder()
                    .username(teacher.getEmail())
                    .password(teacher.getPassword())
                    .roles(teacher.getRole().name())
                    .build();
        }
        
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(admin.getRole().name())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
