package com.tms.service.task;

import com.tms.dto.task.TaskCriteria;
import com.tms.dto.task.TaskDTO;
import com.tms.dto.task.TaskSummary;

import java.util.List;

public interface TaskService {

    void addTask(TaskDTO taskDTO);

    void deleteTask(String code);

    TaskDTO loadTask(String code);

    List<TaskDTO> searchTasks(TaskCriteria taskCriteria);

    TaskSummary getTaskSummary();

    List<TaskDTO> getOverDueTasks();
}
