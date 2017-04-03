package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import lt.damss.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private final RegistrationFormRepository repository;

    private final NotificationService notificationService;

    @Autowired
    MainController(RegistrationFormRepository repository,
                   NotificationService service) {
        this.repository = repository;
        this.notificationService = service;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<RegistrationForm> getHelloMessage() {
        return repository.findAll();
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    ResponseEntity<?> registerForm(@RequestBody RegistrationForm form) {

        try {
            form.setUniqueCode();
            RegistrationForm result = repository.save(form);


            notificationService.sendNotification(form);

//            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("/home/paulius/Projects/confreg-api/Beans.xml");
//
//            RegisteredEventPublisher cvp = (RegisteredEventPublisher) context.getBean("customEventPublisher");
//
//            cvp.publish(form);

            //Return response
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(result.getId()).toUri();

            return new ResponseEntity<Object>(result, HttpStatus.OK);

        } catch (Exception e) {
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }



    }
}
