package lt.damss.reports;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Title - Bold, Times New Roman, 13  (18 above, below 4)
 * Authors - Times New Roman, 11 (4 below)
 * Affiliates - Times New Roman, 10 (each in a new line) (none)
 * Email - Courier New, 10 (4 below)
 * Abstract - Times New Roman, 10 (none)
 *
 * 
 */
public class DocReportGenerator {
    private XWPFDocument document;

    public DocReportGenerator() {
        this.document = new XWPFDocument();
    }

    public void addEntry(String title, String authors, String affiliates, String emails, String reportAbstract){
        writeTitle(title);
        writeAuthors(authors);
        writeAffiliates(affiliates);
        writeEmails(emails);
        writeAbstract(reportAbstract);
    }

    public void generateReport(){

        try {
            FileOutputStream out = new FileOutputStream(new File("report.docx"));
            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTitle(String title){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingBefore(360);
        paragraph.setSpacingAfter(80);
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setBold(true);
        run.setText(title);
    }

    private void writeAuthors(String authors){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(80);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(11);
        run.setFontFamily("Times New Roman");
        run.setText(authors);
    }

    private void writeAffiliates(String affiliates){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(0);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setFontFamily("Times New Roman");
        run.setText(affiliates);
    }

    private void writeEmails(String emails){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(80);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setFontFamily("Courier New");
        run.setText(emails);
    }

    private void writeAbstract(String reportAbstract){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingAfter(0);
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(10);
        run.setText(reportAbstract);
    }
}
