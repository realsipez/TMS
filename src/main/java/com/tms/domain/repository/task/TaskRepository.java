package com.tms.domain.repository.task;

import com.tms.domain.enums.TaskPriority;
import com.tms.domain.enums.TaskStatus;
import com.tms.domain.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByCode(String code);

    void deleteByCode(String code);

    @Query("select t from Task t where t.dueDate < :date")
    List<Task> findAllOverDueTasks(@Param("date") String date);

    List<Task> findAllByStatusOrPriorityOrDueDate(TaskStatus status, TaskPriority priority, String dueDate);
}
