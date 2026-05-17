package simple.food.backend.infrastructur.email;

import java.util.Map;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.*;

@Async
@Service
public class SpringMailSenderService {

    private static final Logger log = LoggerFactory.getLogger(SpringMailSenderService.class);

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SpringMailSenderService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendWelcomeEmail(String to, String userName, String actionUrl) {
        String subject = "Bem-vindo ao SimpleFood!";
        String templateName = "welcome-email";

        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", userName);
        variables.put("appName", "SimpleFood");
        variables.put("actionUrl", actionUrl);

        sendEmailWithTemplate(to, subject, templateName, variables);
    }

    private void sendEmailWithTemplate(String to, String subject, String templateName,
                                       Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            log.info("Enviando e-mail com template '{}' para {}", templateName, to);
            mailSender.send(mimeMessage);
            log.info("E-mail enviado com sucesso para {}", to);

        } catch (MessagingException e) {
            log.error("Falha ao enviar e-mail (sem anexo) para {}: {}", to, e.getMessage());
        }
    }
}
