package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    long saveSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAll(String author, String modificationDate);

    ScheduleResponseDto findScheduleById(Long id);

    long updateSchedule(Long id, ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, String password);
}
