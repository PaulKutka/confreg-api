package lt.damss.reports;

import lt.damss.models.RegistrationForm;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.print.AttributeException;
import java.io.*;

/**
 * 1 - Emails.
 * 2 - Name, Surename, Institution, Room Type.
 */
public class EmailReportGenerator implements AttendeeReport{
    XSSFWorkbook workbook;
    private int rowNumber;
    private XSSFSheet sheet;
    private File file;

    public EmailReportGenerator() {
        this.workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        this.rowNumber = 0;
        file = new File("Reports Data/emailsReport.xlsx");
    }

    private void addEntry(String email){
        XSSFRow row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(email);
        rowNumber++;
    }

    @Override
    public void addAttendees(Iterable<RegistrationForm> atendeeForms) {
        for (RegistrationForm rf :
                atendeeForms) {
            this.addEntry(rf.getEmail());
        }
        generateReport();
    }

    public void generateReport(){
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile() {
        return this.file;
    }
}
