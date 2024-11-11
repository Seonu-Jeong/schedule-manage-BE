package com.example.schedule.repository;

import com.example.schedule.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByAuthorAndPassword(String author, String password);
}
