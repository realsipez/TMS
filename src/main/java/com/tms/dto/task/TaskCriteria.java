package com.tms.dto.task;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskCriteria implements Serializable {

    private String status;
    private String priority;
    private String dueDate;
}
