package lt.damss.reports;

import lt.damss.models.RegistrationForm;

import java.io.File;


/**
 * Created by lukas on 17.4.13.
 */
public interface AttendeeReport {

    void addAttendees(Iterable<RegistrationForm> atendeeForms);
    void generateReport();
    File getFile();
}
