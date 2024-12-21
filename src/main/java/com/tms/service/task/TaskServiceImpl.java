package com.tms.service.task;

import com.tms.domain.enums.TaskPriority;
import com.tms.domain.enums.TaskStatus;
import com.tms.domain.model.task.Task;
import com.tms.domain.model.user.User;
import com.tms.domain.repository.task.TaskRepository;
import com.tms.dto.task.TaskCriteria;
import com.tms.dto.task.TaskDTO;
import com.tms.dto.task.TaskSummary;
import com.tms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    @Override
    public void addTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setCode(taskDTO.getCode());
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setDueDate(taskDTO.getDueDate());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        User assignee = userService.loadUserByUsername(taskDTO.getAssigneeUserName());
        task.setAssignee(assignee);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(String code) {
        taskRepository.deleteByCode(code);
    }

    @Override
    public TaskDTO loadTask(String code) {
        Optional<Task> task = taskRepository.findByCode(code);
        return mapTaskToDto(task.orElseThrow(
                RuntimeException::new
        ));
    }

    @Override
    public List<TaskDTO> searchTasks(TaskCriteria taskCriteria) {
        List<Task> tasks = taskRepository.findAllByStatusOrPriorityOrDueDate(
                Objects.nonNull(taskCriteria.getStatus()) ? TaskStatus.valueOf(taskCriteria.getStatus()) : null,
                Objects.nonNull(taskCriteria.getPriority()) ? TaskPriority.valueOf(taskCriteria.getPriority()) : null,
                Objects.nonNull(taskCriteria.getPriority()) ? taskCriteria.getDueDate() : null
        );
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task : tasks) {
            TaskDTO taskDTO = mapTaskToDto(task);
            taskDTOS.add(taskDTO);
        }
        return taskDTOS;
    }

    @Override
    public TaskSummary getTaskSummary() {
        TaskSummary taskSummary = new TaskSummary();
        List<TaskDTO> totalTasks = new ArrayList<>();
        taskRepository.findAll().forEach(task -> totalTasks.add(mapTaskToDto(task)));
        taskSummary.setTotalTasks(totalTasks);
        List<TaskDTO> dueTodayTasks = totalTasks.stream().filter(task -> task.getDueDate().equals(LocalDate.now().toString())).toList();
        taskSummary.setDueTodayTasks(dueTodayTasks);
        List<TaskDTO> pendingTasks = totalTasks.stream().filter(task -> task.getStatus().equals(TaskStatus.PENDING)).toList();
        taskSummary.setPendingTasks(pendingTasks);
        List<TaskDTO> completedTasks = totalTasks.stream().filter(task -> task.getStatus().equals(TaskStatus.COMPLETED)).toList();
        taskSummary.setCompletedTasks(completedTasks);
        return taskSummary;
    }

    @Override
    public List<TaskDTO> getOverDueTasks() {
        List<TaskDTO> overDueTasks = new ArrayList<>();
        taskRepository.findAllOverDueTasks(LocalDate.now().toString()).forEach(
                task -> overDueTasks.add(mapTaskToDto(task))
        );
        return overDueTasks;
    }

    private TaskDTO mapTaskToDto(Task task) {
        return new TaskDTO(
                task.getCode(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee().getUsername()
        );
    }
}
