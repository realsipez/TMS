package com.tms.service.task.notification;

import com.tms.dto.task.TaskDTO;
import com.tms.service.task.TaskService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender emailSender;
    private final TaskService taskService;

    public NotificationServiceImpl(JavaMailSender emailSender, TaskService taskService) {
        this.emailSender = emailSender;
        this.taskService = taskService;
    }

    @Override
    public void sendOverDueTaskNotification() {
        List<TaskDTO> overDueTasks = taskService.getOverDueTasks();
        if (!overDueTasks.isEmpty()) {
            StringBuilder text = new StringBuilder();
            text.append("Notice! Uncompleted tasks:\n");
            overDueTasks.forEach(task -> {
                text.append(task.getCode()).append("\n");
            });
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("realsipez@gmail.com");
            message.setTo("realsipez@gmail.com");
            message.setSubject("OverDue Tasks Notification");
            message.setText(text.toString());
            emailSender.send(message);
        }
    }
}
