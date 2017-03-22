package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import lt.damss.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

    //private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private RegistrationFormRepository repository;

    @Autowired
    private NotificationService notificationService;



    @RequestMapping(value = "/", method =  RequestMethod.GET)
    public Iterable<RegistrationForm> getHelloMessage(){
        return repository.findAll();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public RegistrationForm registerForm(@RequestBody RegistrationForm form, HttpServletResponse response){
        repository.save(form);

        try {
            notificationService.sendNotification(form);
        } catch (MessagingException e){
            // cath error
            //logger.info("Error Sending Email:" + e.getMessage());
        }



       return form;
    }
}
