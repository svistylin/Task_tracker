package com.tracker.task.tasktracker.service.impl;


import com.tracker.task.tasktracker.dto.UserDto;
import com.tracker.task.tasktracker.entity.Role;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.RoleRepository;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.service.UserService;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto save(UserDto userDto, String roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(NoSuchElementException::new);
        if (userRepository.findByEmailAndDeletedFalse(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("This email have already busied ");
        }
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = userDto.toUser();
        User saved = userRepository.save(user);
        saved.setRoles(roles);
        return userRepository.save(saved).toDto();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findByDeletedFalse();
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndDeletedFalse(email);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return Optional
                .ofNullable(userRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(NoSuchElementException::new).toDto());
    }

    @Override
    public UserDto deleteUser(JwtUser jwtUser, UserDto userDto) throws AccessDeniedException {
        if (jwtUser.getId().equals(userDto.getId())) {
            User user = userRepository.findById(userDto.getId()).orElseThrow(NoSuchElementException::new);
            user.setDeleted(true);
            userRepository.save(user);
        } else {
            throw new AccessDeniedException("Error, you can delete only own account");
        }
        return userDto;
    }

    @Override
    public Page<User> findByRange(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public UserDto update(JwtUser jwtUser, UserDto userDto) {
        if (jwtUser.getId().equals(userDto.getId())) {
            User user = userRepository.findById(jwtUser.getId()).orElseThrow(NoSuchElementException::new);
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            return userRepository.save(user).toDto();
        } else throw new PermissionDeniedDataAccessException("You can update only own account", new Throwable());
    }
}
