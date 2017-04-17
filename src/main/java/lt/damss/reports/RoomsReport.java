package lt.damss.reports;

import lt.damss.models.RegistrationForm;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class RoomsReport implements AttendeeReport {
    XSSFWorkbook workbook;
    private int rowNumber;
    private XSSFSheet sheet;
    private File file;

    public RoomsReport() {
        this.workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        this.rowNumber = 0;

        this.file = new File("reports_data/roomsReport.xlsx");
    }

    public void addEntry(String name, String surename, String institution, String roomType) {
        XSSFRow row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(surename);
        row.createCell(2).setCellValue(institution);
        row.createCell(3).setCellValue(roomType);
        rowNumber++;
    }

    @Override
    public void addAttendees(Iterable<RegistrationForm> atendeeForms) {
        for (RegistrationForm rf :
                atendeeForms) {
            addEntry(rf.getFirstName(),
                    rf.getLastName(),
                    rf.getBillInstitution(),
                    rf.getRoomType());
        }
        generateReport();
    }

    public void generateReport() {
        try {
            FileOutputStream out = new FileOutputStream(this.file);
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
        return file;
    }
}
