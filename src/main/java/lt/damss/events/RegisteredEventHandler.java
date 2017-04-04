package lt.damss.events;

import lt.damss.service.NotificationService;
import org.springframework.context.ApplicationListener;

/**
 * Created by paulius on 17.3.27.
 */
public class RegisteredEventHandler implements ApplicationListener<RegisteredEvent>{





    public void onApplicationEvent(RegisteredEvent event) {

        NotificationService service = new NotificationService();
        //Send Email
        try {
        service.sendNotification(event.getForm());
        } catch (Exception e) {

        }


    }
}
