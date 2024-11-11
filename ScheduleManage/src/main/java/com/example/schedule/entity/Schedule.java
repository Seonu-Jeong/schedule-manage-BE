package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;

    public Schedule(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.userId = schedule.getUserId();
    }
}
