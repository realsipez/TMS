package com.tms.dto.task;

import com.tms.domain.enums.TaskPriority;
import com.tms.domain.enums.TaskStatus;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class TaskDTO implements Serializable {

    @NonNull
    private String code;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private String dueDate;

    @NonNull
    private TaskStatus status;

    @NonNull
    private TaskPriority priority;

    @NonNull
    private String assigneeUserName;
}
