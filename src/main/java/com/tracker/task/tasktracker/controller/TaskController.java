package com.tracker.task.tasktracker.controller;

import com.tracker.task.tasktracker.dto.TaskDto;
import com.tracker.task.tasktracker.entity.Status;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/filter")
    public List<TaskDto> filter(@RequestParam(required = false) String status,
                                @RequestParam(required = false) LocalDate usersCreatedDate,
                                @RequestParam(required = false) boolean getUsersWitchCreatedEarlierThan) {
        return taskService.filter(status, usersCreatedDate, getUsersWitchCreatedEarlierThan);
    }

    @GetMapping("/tasks/all")
    public List<TaskDto> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/tasks")
    public TaskDto getById(Long id) {
        return taskService.findById(id);
    }

    @DeleteMapping("/tasks/delete")
    public TaskDto delete(TaskDto taskDto) {
        return taskService.delete(taskDto);
    }

    @PostMapping("/tasks/create")
    public TaskDto create(TaskDto taskDto, @AuthenticationPrincipal JwtUser jwtUser) {
        return taskService.save(taskDto, jwtUser);
    }

    @PutMapping("/tasks/update")
    public TaskDto updateTaskInfo(TaskDto taskDto) {
        return taskService.update(taskDto);
    }

    @PutMapping("/tasks/status/update")
    public TaskDto changeStatus(Long taskId, String status) {
        return taskService.changeStatus(taskId, Status.valueOf(status));
    }

    @PutMapping("/tasks/maker/update")
    public TaskDto changeMaker(Long taskId, Long userId) {
        return taskService.changeMaker(taskId, userId);
    }
}
