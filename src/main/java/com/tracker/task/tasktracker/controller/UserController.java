package com.tracker.task.tasktracker.controller;

import com.tracker.task.tasktracker.dto.UserDto;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private static final int pageSize = 10;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public UserDto registration(UserDto userDto) {
        return userService.save(userDto, "ROLE_USER");
    }


    @PutMapping("/users/update")
    public UserDto update(UserDto userDto, JwtUser jwtUser) {
        return userService.update(jwtUser, userDto);
    }

    @DeleteMapping("/users/delete")
    public UserDto delete(UserDto userDto, JwtUser jwtUser) throws AccessDeniedException {
        return userService.deleteUser(jwtUser, userDto);
    }

    @GetMapping("/users/{id}/info")
    public UserDto getUsersInfo(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/users/all")
    public List<User> getAll(@RequestParam Integer pageId) {
        Pageable pageable = PageRequest.of(pageSize * (pageId - 1), pageSize * pageId);
        return userService.findByRange(pageable).getContent();
    }
}
