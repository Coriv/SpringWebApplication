package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailCreatorService {

    private final AdminConfig adminConfig;
    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;
    private final DbService dbService;
    private final String TASKS_ULR = "https://coriv.github.io";

    public String buildTrelloCardEmail(Mail mail) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        context.setVariable("task_url", mail.getShortUrl());
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message", "Regards");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduledDatabaseInfoMail(Mail mail) {
        List<Task> tasks = dbService.getAllTask();

        List<String> tasksTitle = tasks.stream()
                .filter(task -> tasks.indexOf(task) < 5)
                .map(task -> task.getTitle())
                .collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("message", mail.getMessage());
        context.setVariable("tasks_url", TASKS_ULR);
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("tasks_list", tasksTitle);
        context.setVariable("goodbye_message", "Regards");

        return templateEngine.process("mail/info-database-mail", context);
    }
}
