package com.example.schedule.repository;

import com.example.schedule.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByAuthorAndPassword(String author, String password);

    Optional<User> findUserByScheduleId(Long id);

    void updateUserName(Long id, String author);
}
