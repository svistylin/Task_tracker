package com.tracker.task.tasktracker.service.impl;

import com.tracker.task.tasktracker.dto.TaskDto;
import com.tracker.task.tasktracker.entity.Status;
import com.tracker.task.tasktracker.entity.Task;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.TaskRepository;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.service.TaskService;
import com.tracker.task.tasktracker.specification.TaskSpecification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
                .toDto();
    }

    @Override
    public TaskDto save(TaskDto taskDto, JwtUser jwtUser) {
        User user = userRepository.findByIdAndDeletedFalse(jwtUser.getId()).orElseThrow(NoSuchElementException::new);
        Task save = taskRepository.save(taskDto.toTask());
        save.setMaker(user);
        return taskRepository.save(save).toDto();
    }

    @Override
    public TaskDto update(TaskDto taskDto) {
        Task task = taskDto.toTask();
        Task toUpdate = taskRepository.findByIdAndDeletedFalse(taskDto.getId()).orElseThrow(NoSuchElementException::new);
        toUpdate.setTitle(task.getTitle());
        toUpdate.setDescription(task.getDescription());
        toUpdate.setDeadlineDate(task.getDeadlineDate());
        toUpdate.setStatus(task.getStatus());
        return taskRepository.save(toUpdate).toDto();
    }

    @Override
    public TaskDto delete(TaskDto taskDto) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskDto.getId()).orElseThrow(NoSuchElementException::new);
        task.setDeleted(true);
        return taskRepository.save(task).toDto();
    }

    @Override
    public List<TaskDto> filter(String status, LocalDate usersCreatedDate, boolean getOlder) {
        Status stat = Status.valueOf(status);
        TaskSpecification taskSpecification = new TaskSpecification();
        taskSpecification.setStatus(stat);
        taskSpecification.setGetUsersWitchCreatedEarlierThan(getOlder);
        taskSpecification.setUsersCreatedDate(usersCreatedDate);
        return taskRepository.findAll(taskSpecification).stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto changeStatus(Long taskId, Status status) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId).orElseThrow(NoSuchElementException::new);
        task.setStatus(status);
        return taskRepository.save(task).toDto();
    }

    @Override
    public TaskDto changeMaker(Long taskId, Long newMaker) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findByIdAndDeletedFalse(taskId).orElseThrow(NoSuchElementException::new);
        task.setMaker(user);
        return taskRepository.save(task).toDto();
    }
}
