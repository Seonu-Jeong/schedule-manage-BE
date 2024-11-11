package com.example.schedule.service.impl;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import com.example.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.InputMismatchException;
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

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    @Override
    public long updateSchedule(Long id, ScheduleRequestDto requestDto)
            throws FindException, NoSuchElementException {

        if(scheduleRepository.findScheduleById(id) == null)
            throw new FindException("존재하지 않는 게시물입니다.");

        User user = userRepository.findUserByScheduleId(id)
                .orElseThrow(()->new FindException("존재하지 않는 유저입니다."));

        if ( !user.getPassword().equals(requestDto.getPassword()) ){
            throw new NoSuchElementException("비밀번호 불일치");
        }

        userRepository.updateUserName(user.getId(), requestDto.getAuthor());

        scheduleRepository.updateSchedule(id, requestDto);

        return id;
    }

    @Override
    public void deleteSchedule(Long id, String password) {

        User user = userRepository.findUserByScheduleId(id)
                .orElseThrow(()->new FindException("존재하지 않는 유저입니다."));

        if( !user.getPassword().equals(password) ){
            System.out.println(user.getPassword());
            throw new NoSuchElementException("비밀번호 불일치");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
