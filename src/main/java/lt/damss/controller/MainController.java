package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import lt.damss.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;


/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private final RegistrationFormRepository repository;

    private final NotificationService notificationService;

    //Variables for generating random string
    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String appendString = "DAMSS-";
    private final int codeLength = 8;
    SecureRandom rnd = new SecureRandom();


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
            //Set unique code for a form
            form.setUniqueCode(appendString + randomString(codeLength));

            //Save form to a database
            RegistrationForm result = repository.save(form);

            //Send an email
            notificationService.sendNotification(form);


            return new ResponseEntity<Object>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    private String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

}
