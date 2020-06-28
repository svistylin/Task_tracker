package com.tracker.task.tasktracker.service;


import com.tracker.task.tasktracker.dto.RoleDto;
import com.tracker.task.tasktracker.entity.Role;

public interface RoleService {

    Role addRole(RoleDto roleDto);

    RoleDto getById(Long roleId);
}
