package com.example.schedule.service.impl;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import com.example.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository){
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long saveSchedule(ScheduleRequestDto scheduleRequestDto) throws NoSuchElementException {
        Optional<User> user = userRepository.findByAuthorAndPassword(
                scheduleRequestDto.getAuthor(), scheduleRequestDto.getPassword());

        user.orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다"));

        return scheduleRepository.saveSchedule(user.get().getId(), scheduleRequestDto.getTodo());
    }

    @Override
    public List<ScheduleResponseDto> findAll(String author, String modificationDate) {
        return scheduleRepository.findScheduleByAuthorAndModificationDate(author, modificationDate);
    }
}
