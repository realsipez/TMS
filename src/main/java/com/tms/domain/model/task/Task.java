package com.tms.domain.model.task;

import jakarta.persistence.*;
import lombok.Data;
import com.tms.domain.enums.TaskPriority;
import com.tms.domain.enums.TaskStatus;
import com.tms.domain.model.user.User;

@Data
@Table
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String dueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private TaskPriority priority;

    @ManyToOne
    private User assignee;
}
