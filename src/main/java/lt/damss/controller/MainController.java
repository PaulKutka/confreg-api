package lt.damss.controller;

import lt.damss.models.RegistrationForm;
import lt.damss.reports.DocReportGenerator;
import lt.damss.reports.EmailReportGenerator;
import lt.damss.reports.ExcelReport;
import lt.damss.service.RegistrationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by paulius on 17.3.11.
 */
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private RegistrationService registrationService;



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

        String code = "";

        try {
            JSONObject json = new JSONObject(uniqueCode);
            code = json.getString("uniqueCode");

        } catch (Exception e) {

        }
        RegistrationForm result = registrationService.findByUniqueCode(code);

        if (result != null) {
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/file/3", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadReport(){
        ExcelReport rep = new ExcelReport();

        for (RegistrationForm rf :
                registrationService.getAllForms()) {

            rep.addEntry(rf.getFirstName(), rf.getLastName(), rf.getBillInstitution(), rf.getRoomType());
        }
        rep.generateReport();

        File file = new File("report.xlsx");
        byte[] document = new byte[0];
        try {
            document = FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "xlsx"));
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }



    @RequestMapping(value = "/file/2", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadEmails(){
        EmailReportGenerator rep = new EmailReportGenerator();

        for (RegistrationForm rf :
                registrationService.getAllForms()) {

            rep.addEntry(rf.getEmail());
        }
        rep.generateReport();

        File file = new File("emails.xlsx");
        byte[] document = new byte[0];
        try {
            document = FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "xlsx"));
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }

    @RequestMapping(value = "/file/1", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadWord(){
        DocReportGenerator rep = new DocReportGenerator();


        for (RegistrationForm rf :
                registrationService.getAllForms()) {
            rep.addEntry(rf.getMessageName(),
                    rf.getMessageAuthorsAndAffiliations(),
                    rf.getMessageAuthorsAndAffiliations(),
                    rf.getEmail(),
                    rf.getMessageSummary());
        }
        rep.generateReport();

        File file = new File("report.docx");
        byte[] document = new byte[0];
        try {
            document = FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "docx"));
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }



}
