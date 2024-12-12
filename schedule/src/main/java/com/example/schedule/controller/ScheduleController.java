package com.example.schedule.controller;

import com.example.schedule.model.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// !!!
// НЕ ЮЗАБЕЛЬНЫЙ, С ПРЕД ВЕРСИИ
// !!!

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/{className}")
    public ResponseEntity<?> getSchedule(@PathVariable String className) {
        Optional<Schedule> schedule = scheduleRepository.findByClassName(className);

        if (schedule.isEmpty()) {
            return ResponseEntity.status(404).body("Class not found");
        }

        return ResponseEntity.ok(schedule.get().getLessons());
    }

    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody Schedule schedule) {
        scheduleRepository.save(schedule);
        return ResponseEntity.status(201).body("Schedule added successfully");
    }
}
