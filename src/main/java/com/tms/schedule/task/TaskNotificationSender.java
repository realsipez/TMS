package com.tms.schedule.task;

import com.tms.service.task.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class TaskNotificationSender {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final NotificationService notificationService;

    @Scheduled(cron = "0 00 10 15 * ?")
    public void notifyOverDueTasks() {
        log.info("Sending notification for over due tasks... {}", dateFormat.format(new Date()));
        notificationService.sendOverDueTaskNotification();
    }

}
