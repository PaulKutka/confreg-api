package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import lt.damss.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Locale;

/**
 * Created by paulius on 17.3.11.
 */
@RestController
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private RegistrationFormRepository repository;

    @Autowired
    private NotificationService notificationService;



    @RequestMapping(value = "/", method =  RequestMethod.GET)
    public Iterable<RegistrationForm> getHelloMessage(){
        return repository.findAll();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public RegistrationForm registerForm(@RequestBody RegistrationForm form){
        repository.save(form);

        try {
            notificationService.sendNotification(form);
        } catch (MessagingException e){
            // cath error
            logger.info("Error Sending Email:" + e.getMessage());
        }

       return form;
    }
}
