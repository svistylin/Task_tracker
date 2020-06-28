package com.tracker.task.tasktracker.repository;

import com.tracker.task.tasktracker.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAll(Specification<Task> specification);

    Optional<Task> findByIdAndDeletedFalse(Long id);
}
