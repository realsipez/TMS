package com.tms.dto.task;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskSummary implements Serializable {
    private List<TaskDTO> totalTasks;
    private List<TaskDTO> pendingTasks;
    private List<TaskDTO> completedTasks;
    private List<TaskDTO> dueTodayTasks;
}
