package lt.damss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by paulius on 17.3.11.
 */
@RestController
public class MainController {
    @RequestMapping(value = "/", method =  RequestMethod.GET)
    public String getHelloMessage(){

        return "Hell world!";
    }
}
