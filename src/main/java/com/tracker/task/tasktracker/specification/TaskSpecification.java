package com.tracker.task.tasktracker.specification;

import com.tracker.task.tasktracker.entity.Status;
import com.tracker.task.tasktracker.entity.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification implements Specification<Task> {
    private Status status;
    private boolean getUsersWitchCreatedEarlierThan;
    private LocalDate usersCreatedDate;

    public TaskSpecification() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isGetUsersWitchCreatedEarlierThan() {
        return getUsersWitchCreatedEarlierThan;
    }

    public void setGetUsersWitchCreatedEarlierThan(boolean getUsersWitchCreatedEarlierThan) {
        this.getUsersWitchCreatedEarlierThan = getUsersWitchCreatedEarlierThan;
    }

    public LocalDate getUsersCreatedDate() {
        return usersCreatedDate;
    }

    public void setUsersCreatedDate(LocalDate usersCreatedDate) {
        this.usersCreatedDate = usersCreatedDate;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> conditions = new ArrayList<>();
        if (this.usersCreatedDate != null) {
            if (this.getUsersWitchCreatedEarlierThan) {
                conditions.add(cb.lessThan(root.get("user").get("created"), this.usersCreatedDate));
            } else {
                conditions.add(cb.greaterThan(root.get("user").get("created"), this.usersCreatedDate));
            }
        }
        if (this.status != null) {
            conditions.add(cb.equal(root.get("status"), this.status));
        }
        conditions.add(cb.equal(root.get("deleted"), Boolean.FALSE));
        return cb.and(conditions.toArray(new Predicate[conditions.size()]));
    }
}

