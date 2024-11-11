package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.service.ScheduleService;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    final private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        JsonObject obj = new JsonObject();

        obj.addProperty("id", scheduleService.saveSchedule(scheduleRequestDto));

        return obj.toString();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        JsonObject obj = new JsonObject();

        obj.addProperty("error_msg", e.getMessage());

        return new ResponseEntity<>(obj.toString(), HttpStatus.UNAUTHORIZED);
    }

}