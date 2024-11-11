package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String todo;
    private String author;
    private String password;
}
