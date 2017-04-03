package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private RegistrationService registrationService;

//    @Autowired
//    MainController(RegistrationService registrationService) {
//        this.registrationService = registrationService;
//    }




    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<RegistrationForm> getAllForms() {
        Iterable<RegistrationForm> forms = registrationService.getAllForms();
        return forms;
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    ResponseEntity<?> registerForm(@RequestBody RegistrationForm form) {


        RegistrationForm result = registrationService.registerForm(form);


        if (result != null) {
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);


    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ResponseEntity<?> findById(@RequestBody String uniqueCode) {

           RegistrationForm result = registrationService.findByUniqueCode(uniqueCode);

            if(result != null) {
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            }

            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }


}
