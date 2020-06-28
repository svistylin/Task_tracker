package com.tracker.task.tasktracker.dto;

import com.tracker.task.tasktracker.entity.Task;

import java.time.LocalDate;

public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadlineDate;
    private UserDto maker;

    public Task toTask() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDeadlineDate(this.deadlineDate);
        return task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public UserDto getMaker() {
        return maker;
    }

    public void setMaker(UserDto maker) {
        this.maker = maker;
    }
}
