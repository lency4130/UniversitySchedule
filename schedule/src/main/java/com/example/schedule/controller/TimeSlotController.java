package com.example.schedule.controller;

import com.example.schedule.model.TimeSlot;
import com.example.schedule.repository.TimeSlotRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotController {
	
	private final TimeSlotRepository repository;

    public TimeSlotController(TimeSlotRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping
    public List<TimeSlot> getSlots() {
        return repository.findAll();
    }
	
}
