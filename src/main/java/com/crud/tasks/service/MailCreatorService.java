package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailCreatorService {

    private final AdminConfig adminConfig;
    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message, String url) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", url);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_email", adminConfig.getAdminMail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("goodbye_message", "Regards");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
