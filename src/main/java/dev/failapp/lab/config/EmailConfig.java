package dev.failapp.lab.config;

import com.sendgrid.SendGrid;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EmailConfig {

    @ConfigProperty(name = "config.mail.service.api.key", defaultValue = "")
    private String mailApiKey;

    @ConfigProperty(name = "config.mail.service.from")
    private String mailFrom;

    @ConfigProperty(name = "config.mail.service.template.id")
    private String mailTemplateId;

    @Produces
    public SendGrid sendGrid() {
        return new SendGrid(mailApiKey);
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String getMailTemplateId() {
        return mailTemplateId;
    }
}
