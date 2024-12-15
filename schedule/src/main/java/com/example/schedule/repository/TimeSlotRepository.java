package com.example.schedule.repository;

import com.example.schedule.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
