package com.tracker.task.tasktracker.service.impl;

import com.tracker.task.tasktracker.dto.RoleDto;
import com.tracker.task.tasktracker.entity.Role;
import com.tracker.task.tasktracker.repository.RoleRepository;
import com.tracker.task.tasktracker.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(RoleDto roleDto) {
        return roleRepository.save(roleDto.toRole());
    }

    @Override
    public RoleDto getById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(NoSuchElementException::new).toDto();
    }
}
