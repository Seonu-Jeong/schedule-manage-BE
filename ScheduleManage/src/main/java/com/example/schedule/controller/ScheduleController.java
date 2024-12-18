package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.FindException;
import java.util.InputMismatchException;
import java.util.List;
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
    public ResponseEntity<String> InputExceptionHandler(Exception e) {
        JsonObject obj = new JsonObject();

        obj.addProperty("error_msg", e.getMessage());

        return new ResponseEntity<>(obj.toString(), HttpStatus.UNAUTHORIZED);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ScheduleResponseDto> findScheduleList(
            @RequestParam(value = "modification_date", required = false) String modificationDate,
            @RequestParam String author) {

        List<ScheduleResponseDto> responseList = scheduleService.findAll(author, modificationDate);

        return responseList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public String updateSchedule(
            @PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {

        JsonObject obj = new JsonObject();

        obj.addProperty("id", scheduleService.updateSchedule(id, requestDto));

        return obj.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id,  @RequestBody String password) throws JsonProcessingException {


        // ObjectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 파일을 Java 객체로 읽기
        JsonNode json = objectMapper.readTree(password);

        scheduleService.deleteSchedule(id, json.get("password").asText());

        JsonObject obj = new JsonObject();

        obj.addProperty("id", id);

        return obj.toString();
    }

    @ExceptionHandler({FindException.class, JsonProcessingException.class})
    public ResponseEntity<String> NoExistExceptionHandler(Exception e) {
        JsonObject obj = new JsonObject();

        obj.addProperty("error_msg", e.getMessage());

        return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);
    }
}
