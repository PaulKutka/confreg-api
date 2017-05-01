package lt.damss.events;

import lt.damss.models.RegistrationForm;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Created by paulius on 17.3.27.
 */
public class RegisteredEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    public void setApplicationEventPublisher (ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    public void publish(RegistrationForm form) {
        RegisteredEvent registeredEvent = new RegisteredEvent(this, form);
        publisher.publishEvent(registeredEvent);
    }
}
