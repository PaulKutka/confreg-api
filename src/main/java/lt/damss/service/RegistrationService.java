package lt.damss.service;

import lt.damss.models.RegistrationForm;
import lt.damss.repositories.RegistrationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;


/**
 * Created by paulius on 17.4.3.
 */
@Service
public class RegistrationService {

    @Resource
    private final RegistrationFormRepository repository;

    @Resource
    private final NotificationService notificationService;

    @Autowired
    RegistrationService(RegistrationFormRepository repository,
                        NotificationService service) {
        this.repository = repository;
        this.notificationService = service;
    }


    //Variables for generating random string
    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String appendString = "DAMSS-";
    private final int codeLength = 8;
    SecureRandom rnd = new SecureRandom();

    public RegistrationForm registerForm(RegistrationForm form) {

        try {
            //Set unique code for a form
            form.setUniqueCode(appendString + randomString(codeLength));

            //Save form to a database
            RegistrationForm result = repository.save(form);

            //Send an email
            notificationService.sendNotification(form);

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Iterable<RegistrationForm> getAllForms() {

        return repository.findAll();
    }


    public RegistrationForm findByUniqueCode(String uniqueCode){

        RegistrationForm result = repository.findByUniqueCode(uniqueCode);

        return result;
    }


    private String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
