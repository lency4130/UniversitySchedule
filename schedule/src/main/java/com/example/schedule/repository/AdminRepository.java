package com.example.schedule.repository;

import com.example.schedule.model.Admin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String admin);

}
