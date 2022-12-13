package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.respository.TaskRepository;
import com.crud.tasks.service.SimpleMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailScheduler {
    private final SimpleMailService emailService;
    private final AdminConfig adminConfig;
    private final TaskRepository taskRepository;
    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        emailService.send(Mail.builder()
                .mailTo(adminConfig.getAdminMail())
                .subject(SUBJECT)
                .message(messageBuilder(size))
                .build());
    }

    private String messageBuilder(long size) {
        String result = "Currently in database you got: " + size;
        if (size == 1) {
            return result + " task";
        }
        return result + " tasks";
    }
}
