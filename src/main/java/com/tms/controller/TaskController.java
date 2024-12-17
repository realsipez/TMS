package com.tms.controller;

import com.tms.dto.task.TaskCriteria;
import com.tms.dto.task.TaskDTO;
import com.tms.dto.task.TaskSummary;
import com.tms.service.task.TaskService;
import com.tms.service.task.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final NotificationService notificationService;

    public TaskController(TaskService taskService, NotificationService notificationService) {
        this.taskService = taskService;
        this.notificationService = notificationService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody TaskDTO taskDTO) {
        taskService.addTask(taskDTO);
        return ResponseEntity.ok("Task created successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeTask(@RequestParam String code) {
        taskService.deleteTask(code);
        return ResponseEntity.ok("Task removed successfully");
    }

    @PostMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTask(@RequestBody TaskCriteria criteria) {
        List<TaskDTO> tasks = taskService.searchTasks(criteria);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/summary")
    public ResponseEntity<TaskSummary> getTaskSummary() {
        TaskSummary summary = taskService.getTaskSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/notify")
    public ResponseEntity<String> notifyOverDueTasks() {
        notificationService.sendOverDueTaskNotification();
        return ResponseEntity.ok("Task notification sent successfully");
    }
}
