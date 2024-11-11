package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;

public interface ScheduleService {
    long saveSchedule(ScheduleRequestDto scheduleRequestDto);
}
