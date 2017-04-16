package lt.damss.events;

import lt.damss.models.RegistrationForm;
import org.springframework.context.ApplicationEvent;

/**
 * Created by paulius on 17.3.27.
 */
public class RegisteredEvent extends ApplicationEvent {

    RegistrationForm form;

    public RegisteredEvent(Object source, RegistrationForm form){
        super(source);
        this.form = form;
    }

    public RegistrationForm getForm(){
        return this.form;
    }
}
