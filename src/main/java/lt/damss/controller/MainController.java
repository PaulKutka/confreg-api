package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by paulius on 17.3.11.
 */
@RestController
public class MainController {

    @Autowired
    private RegistrationFormRepository repository;

    @RequestMapping(value = "/", method =  RequestMethod.GET)
    public Iterable<RegistrationForm> getHelloMessage(){
        return repository.findAll();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public RegistrationForm registerForm(@RequestBody RegistrationForm form){
        repository.save(form);
        return form;
    }
}
