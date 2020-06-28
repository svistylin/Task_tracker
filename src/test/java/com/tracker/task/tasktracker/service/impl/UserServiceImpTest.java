package com.tracker.task.tasktracker.service.impl;

import com.tracker.task.tasktracker.dto.UserDto;
import com.tracker.task.tasktracker.entity.Role;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.RoleRepository;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.service.UserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class UserServiceImpTest {


    UserService userService;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    UserDto userDto;

    JwtUser jwtUser;


    @BeforeEach
    void init() {
        userService = new UserServiceImp(userRepository, roleRepository);
        Mockito.when(userRepository.findByEmailAndDeletedFalse("vasya"))
                .thenReturn(Optional.ofNullable(new User()));
        userDto = new UserDto();
        userDto.setEmail("vasya");
        userDto.setId(1l);
        jwtUser = new JwtUser(1l, "vasya", "asd",
                "qwe", "asdf", "asdfff",
                Collections.emptyList(), true, new Date());
    }


    @Test
    void saveUserWithExistInDbEmail() {
        UserDto user = new UserDto();
        user.setEmail("vasya");
        Assert.assertThrows(NoSuchElementException.class,
                () -> userService.save(user, "ROLE_USER"));
    }

    @Test
    void tryUpdateNonExistedUser() {
        userDto.setEmail("asdqwe");
        Assert.assertThrows(NoSuchElementException.class,
                () -> userService.update( jwtUser,userDto));
        userDto.setEmail("vasya");
    }

    @Test
    void tryDeleteAnotherUser() {
        userDto.setId(23l);
        Assert.assertThrows(AccessDeniedException.class,
                () -> userService.deleteUser( jwtUser,userDto));
        userDto.setId(1l);
    }

    @Test
    void updateAccountAnotherUser() {
        userDto.setId(112l);
        Assert.assertThrows(PermissionDeniedDataAccessException.class,
                () -> userService.update(jwtUser, userDto));
        userDto.setId(1l);
    }

    @Test
    void findBy() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void findByRange() {
    }

    @Test
    void update() {
    }
}