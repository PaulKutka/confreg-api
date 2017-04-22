package lt.damss.service;

import lt.damss.models.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Created by paulius on 17.3.19.
 */
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public NotificationService() {}

    public void sendNotification(RegistrationForm form) throws MailException{



        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("firstName", form.getFirstName());
        ctx.setVariable("lastName", form.getLastName());
        ctx.setVariable("educationalDegree", form.getEducationalDegree());
        ctx.setVariable("email", form.getEmail());
        ctx.setVariable("phoneNumber", form.getPhoneNumber());
        ctx.setVariable("institution", form.getInstitution());
        ctx.setVariable("messageName", form.getMessageName());
        ctx.setVariable("messageAuthorsAndAffiliations", form.getMessageAuthorsAndAffiliations());
        ctx.setVariable("messageSummary", form.getMessageSummary());
        ctx.setVariable("uniqueCode", form.getUniqueCode());



        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            message.setFrom("sender@example.com");
            message.setTo(form.getEmail());
            message.setSubject("Registracijos patvirtinimas");

            final String htmlContent = this.templateEngine.process("email-template", ctx);
            message.setText(htmlContent, true /* isHtml */);
        } catch (MessagingException e){

        }
        this.mailSender.send(mimeMessage);

    }

    public void sendUpdateNotification(RegistrationForm form) throws MailException{



        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("firstName", form.getFirstName());
        ctx.setVariable("lastName", form.getLastName());
        ctx.setVariable("educationalDegree", form.getEducationalDegree());
        ctx.setVariable("email", form.getEmail());
        ctx.setVariable("phoneNumber", form.getPhoneNumber());
        ctx.setVariable("institution", form.getInstitution());
        ctx.setVariable("messageName", form.getMessageName());
        ctx.setVariable("messageAuthorsAndAffiliations", form.getMessageAuthorsAndAffiliations());
        ctx.setVariable("messageSummary", form.getMessageSummary());
        ctx.setVariable("uniqueCode", form.getUniqueCode());



        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            message.setFrom("sender@example.com");
            message.setTo(form.getEmail());
            message.setSubject("Registracijos patvirtinimas");

            final String htmlContent = this.templateEngine.process("update-template", ctx);
            message.setText(htmlContent, true /* isHtml */);
        } catch (MessagingException e){

        }
        this.mailSender.send(mimeMessage);

    }



    public void sendDeletedNotification(String email) throws MailException{



//        // Prepare the evaluation context
        final Context ctx = new Context();
//        ctx.setVariable("email", form.getEmail());




        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            message.setFrom("sender@example.com");
            message.setTo(email);
            message.setSubject("Ištrynimo patvirtinimas");

            final String htmlContent = this.templateEngine.process("deleted-template", ctx);
            message.setText(htmlContent, true /* isHtml */);
        } catch (MessagingException e){

        }

        this.mailSender.send(mimeMessage);

    }





}
