import core.ReportFormatter;

/**
 * В данном классе определена главная точка входа.
 * @args Необходимо передать имена трёх файлов: файл настроек,
 * файл с исходными данными, файл будущего отчета.
 */
public class ReportGenerator {
    public static void main(String[] args) {
      /*  if(args.length == 0) {
            System.out.println("Укажите имена файлов:");
            System.out.println("\t -  конфигурации отчёта;");
            System.out.println("\t -  исходных данных;");
            System.out.println("\t -  отчёта;");
        } else {

        }
        */
        ReportFormatter reportFormatter = new ReportFormatter();
        reportFormatter.getReportSettings("settings.xml");
        reportFormatter.getRawReportData("source-data.tsv");
        reportFormatter.createReport("report.txt");
        reportFormatter.writeDataToReport();
    }
}
