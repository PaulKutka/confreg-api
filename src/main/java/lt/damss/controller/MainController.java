package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.reports.AttendeeReport;
import lt.damss.reports.ReportFactory;
import lt.damss.service.NotificationService;
import lt.damss.service.RegistrationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.MailException;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RegistrationService registrationService;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllForms() {
        Iterable<RegistrationForm> forms = registrationService.getAllForms();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json; charset=utf-8");

        if (forms.iterator().hasNext()) {
            return new ResponseEntity<Iterable<RegistrationForm>>(forms, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).build();
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    ResponseEntity<?> registerForm(@RequestBody @Valid RegistrationForm form,
                                   BindingResult bindingResult) {

        //TODO: RegistrationForm.java sudet likusias validacijas
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        try {
            //Add form to a database
            RegistrationForm result = registrationService.registerForm(form);


            //Send user a notification
            notificationService.sendNotification(form);

            return new ResponseEntity<Object>(result, HttpStatus.OK);


        } catch (Exception e) {
            if (e instanceof MailException || e instanceof MessagingException)
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("There was a problem with sending a mail");
            else
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("Internal server error");

        }


    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateForm(@PathVariable("id") Long id,
                                 @RequestBody @Valid RegistrationForm form,
                                 BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(HttpStatus.valueOf(400));

        }



        try {
            RegistrationForm result = registrationService.updateForm(id, form);
            notificationService.sendUpdateNotification(result);
            return new ResponseEntity<Object>(result, HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof MailException || e instanceof MessagingException)
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("There was a problem with sending a mail");
            else
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("Internal server error");

        }



    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteForm(@PathVariable("id") Long id) {

        try {
            RegistrationForm result = registrationService.deleteForm(id);
            notificationService.sendDeletedNotification(result.getEmail());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body("Form deleted successfully");

        } catch (Exception e) {

        }
        return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
    }


    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ResponseEntity<?> findById(@RequestBody String uniqueCode) {

        String code = "";

        try {
            JSONObject json = new JSONObject(uniqueCode);
            code = json.getString("uniqueCode");

        } catch (Exception e) {

        }
        
        try {
            //Add form to a database
            RegistrationForm result = registrationService.findByUniqueCode(code);

            if (result == null) {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }


            return new ResponseEntity<Object>(result, HttpStatus.OK);


        } catch (Exception e) {
            if (e instanceof MailException || e instanceof MessagingException)
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("There was a problem with sending a mail");
            else
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("Internal server error");

        }

    }

    //TODO: repotu generavimas neveiks jei nebus "Reports Data" folderio
    @RequestMapping(value = "/reports/{reportName}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadReport(@PathVariable("reportName") String fileName) {
        ReportFactory factory = new ReportFactory();
        AttendeeReport report = factory.getReport(fileName);

        if (report != null) {
            report.addAttendees(registrationService.getAllForms());
            byte[] document = new byte[0];
            try {
                document = FileCopyUtils.copyToByteArray(report.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "report"));
            header.set("Content-Disposition", "inline; filename=" + report.getFile().getName());
            header.setContentLength(document.length);

            return new HttpEntity<byte[]>(document, header);
        }
        return null;
    }

}

