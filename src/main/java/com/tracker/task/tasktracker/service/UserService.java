package com.tracker.task.tasktracker.service;
import com.tracker.task.tasktracker.dto.UserDto;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto save(UserDto user, String role);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<UserDto> findById(Long id);

    UserDto update(JwtUser user, UserDto userDto);

    UserDto deleteUser(JwtUser jwtUser, UserDto userDto) throws AccessDeniedException;

    Page<User> findByRange(Pageable pageable);
}
