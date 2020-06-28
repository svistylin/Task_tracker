package com.tracker.task.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tracker.task.tasktracker.entity.Role;
import com.tracker.task.tasktracker.entity.User;

import java.util.List;

public class RoleDto {

    private Long id;

    private String name;

    @JsonIgnore
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role toRole() {
        Role role = new Role();
        role.setName(this.name);
        role.setId(this.id);
        return role;
    }
}
