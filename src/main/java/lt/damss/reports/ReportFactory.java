package lt.damss.reports;

/**
 * Created by lukas on 17.4.13.
 */
public class ReportFactory {

    public AttendeeReport getReport(String reportType){
        if(reportType == null) return null;
        if(reportType.equalsIgnoreCase("MESSEGESREPORT")) return new DocReportGenerator();
        if(reportType.equalsIgnoreCase("EMAILSREPORT")) return new EmailReportGenerator();
        if(reportType.equalsIgnoreCase("ROOMSREPORT")) return new RoomsReport();
        return null;
    }
}
