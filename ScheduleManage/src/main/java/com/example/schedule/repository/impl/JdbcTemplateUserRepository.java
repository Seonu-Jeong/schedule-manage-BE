package com.example.schedule.repository.impl;

import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByAuthorAndPassword(String author, String password) {
        List<User> result = jdbcTemplate.query("select * from user where name = ? and password = ?",
                userRowMapper(), author, password);

        return result.stream().findFirst();
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getObject("creation_date", LocalDateTime.class),
                        rs.getObject("modification_date", LocalDateTime.class)
                );
            }

        };
    }
}
