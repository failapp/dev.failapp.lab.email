package dev.failapp.lab.repository;

import dev.failapp.lab.model.EmailRequest;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class EmailRepository implements PanacheRepository<EmailRequest> {

    public Optional<EmailRequest> findByTo(String to) {
        return find("to", to).firstResultOptional();
    }

}
