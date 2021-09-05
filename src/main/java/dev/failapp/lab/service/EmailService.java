package dev.failapp.lab.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import dev.failapp.lab.config.EmailConfig;
import dev.failapp.lab.model.EmailRequest;
import dev.failapp.lab.repository.EmailRepository;
import dev.failapp.lab.shared.Util;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class EmailService {

    private static final Logger log = Logger.getLogger(EmailService.class);

    @Inject
    private EmailRepository emailRepository;

    @Inject
    private SendGrid sendGrid;

    @Inject
    private EmailConfig emailConfig;

    @Transactional
    public boolean sendEmail(EmailRequest emailRequest) {

        if (Optional.ofNullable(emailRequest).isEmpty())
            return false;

        try {

            Email from = new Email(emailConfig.getMailFrom());
            Email to = new Email(emailRequest.getTo());
            String subject = emailRequest.getSubject();
            Content content = new Content("text/plain", emailRequest.getBody());

            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            log.infof("[x] response status code: %s", response.getStatusCode());
            log.infof("[x] response body: %s", response.getBody());
            log.infof("[x] response headers: %s", response.getHeaders());

            if (response.getStatusCode() == 202) {
                emailRequest.setTimestamp(LocalDateTime.now().format(Util.formatDateTime));
                emailRepository.persist(emailRequest);
                return true;
            }

        } catch (Exception ex) {
            log.infof("[x] error: %s", ex.getMessage());
        }

        return false;
    }

    @Transactional
    public boolean sendEmailTemplate(EmailRequest emailRequest) {

        if (Optional.ofNullable(emailRequest).isEmpty())
            return false;

        try {

            Email from = new Email(emailConfig.getMailFrom());
            Email to = new Email(emailRequest.getTo());
            String subject = emailRequest.getSubject();

            Mail mail = new Mail();
            mail.setFrom(from);

            Personalization personalization = new Personalization();
            personalization.addTo(to);
            mail.addPersonalization(personalization);
            mail.setTemplateId(emailConfig.getMailTemplateId());

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            log.infof("[x] response status code: %s", response.getStatusCode());
            log.infof("[x] response body: %s", response.getBody());
            log.infof("[x] response headers: %s", response.getHeaders());

            if (response.getStatusCode() == 202) {
                emailRequest.setTimestamp(LocalDateTime.now().format(Util.formatDateTime));
                emailRepository.persist(emailRequest);
                return true;
            }

        } catch (Exception ex) {
            log.infof("[x] error: %s", ex.getMessage());
        }

        return false;
    }


}
