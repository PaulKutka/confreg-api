package lt.damss.reports;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * 1 - Emails.
 * 2 - Name, Surename, Institution, Room Type.
 */
public class ExcelReport {
    XSSFWorkbook workbook;
    private int rowNumber;
    private XSSFSheet sheet;

    public ExcelReport() {
        this.workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        this.rowNumber = 0;

    }

    public void addEntry(String name, String surename, String institution, String roomType){
        XSSFRow row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(surename);
        row.createCell(2).setCellValue(institution);
        row.createCell(3).setCellValue(roomType);
        rowNumber++;
    }

    public void generateReport(){
        try {
            FileOutputStream out = new FileOutputStream(new File("report.xlsx"));
            workbook.write(out);

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public byte[] getByteStream(){
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            workbook.write(bos);
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bos.toByteArray();
//    }
}
