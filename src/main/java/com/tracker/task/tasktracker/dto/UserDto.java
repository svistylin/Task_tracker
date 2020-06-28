package com.tracker.task.tasktracker.dto;

import com.tracker.task.tasktracker.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleDto> roleDtos;

    public UserDto() {
    }

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setFirstName(this.firstName);
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setLastName(this.lastName);
        if (this.roleDtos != null) {
            user.setRoles(this.roleDtos.stream()
                    .map(RoleDto::toRole)
                    .collect(Collectors.toList()));
        }
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleDto> getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(List<RoleDto> roleDtos) {
        this.roleDtos = roleDtos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
