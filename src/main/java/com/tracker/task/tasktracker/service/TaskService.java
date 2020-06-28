package com.tracker.task.tasktracker.service;

import com.tracker.task.tasktracker.dto.TaskDto;
import com.tracker.task.tasktracker.entity.Status;
import com.tracker.task.tasktracker.security.jwt.JwtUser;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    TaskDto save(TaskDto taskDto, JwtUser jwtUser);

    TaskDto update(TaskDto taskDto);

    TaskDto delete (TaskDto taskDto);

    List<TaskDto> filter(String status, LocalDate usersCreatedDate, boolean sortedFromOldToNew);

    TaskDto changeStatus(Long taskId, Status status);

    TaskDto changeMaker(Long taskId, Long newExecutorId);
}
