package dev.failapp.lab.resource;

import dev.failapp.lab.model.EmailRequest;
import dev.failapp.lab.service.EmailService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class EmailRest {

    @Inject
    private EmailService emailService;

    @POST
    @Path("/api/v1/email")
    public Response sendEmail(EmailRequest emailRequest) {
        boolean success = emailService.sendEmail(emailRequest);
        if (success) {
            Map<String, Object> map = new HashMap<>();
            map.put("mail to", emailRequest.getTo());
            map.put("status", "send");
            return Response.ok(map).build();
        } else {
            return Response.status(404).build();
        }
    }


    @POST
    @Path("/api/v1/email/template")
    public Response sendEmailTemplate(EmailRequest emailRequest) {

        boolean success = emailService.sendEmailTemplate(emailRequest);
        if (success) {
            Map<String, Object> map = new HashMap<>();
            map.put("mail to", emailRequest.getTo());
            map.put("status", "send");
            return Response.ok(map).build();
        } else {
            return Response.status(404).build();
        }
    }


    @GET
    public Response index() {
        return Response.status(404).build();
    }


}
