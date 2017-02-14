package core;

import entities.Column;
import entities.Page;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

/**
 * Данный класс заполняет отчёт.
 * В ходе работы он формирует символьно-визуальное представление страницы отчёта, а также
 * заполняет её данными, извлечёнными из исходного файла, предоставленного пользователем.
 */
public class ReportFormatter {
    private Page reportPage;
    private List<Column> reportColumns;
    private DataParser dataParser;
    private ReportConfigurator reportConfigurator;
    private BufferedWriter bufWriter;
    private final String encoding = "UTF-16";

    public ReportFormatter(){

    }

    public void getReportSettings(String settingsFile){
        reportConfigurator = new ReportConfigurator(settingsFile);
        reportConfigurator.configureReport();
        reportPage = reportConfigurator.getReportPage();
        reportColumns = reportConfigurator.getReportColumns();
    }

    public void getRawReportData(String rawDataFile){
        dataParser = new DataParser(rawDataFile, encoding);
        dataParser.parseData();
    }



    public void createReport(String reportOutputName){
        try {
            bufWriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(reportOutputName), Charset.forName(encoding) )
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToReport(){
        List<String> value = dataParser.getReportEntries();

        try {
            for( String s : value){
                writePageHeader();
                String[] mass = s.split("\\t");
                bufWriter.write(s);

                bufWriter.write(reportPage.getHorizontalBar());
                bufWriter.write(reportPage.getRightIndent());

                bufWriter.flush();
            }
            bufWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writePageHeader() throws IOException{
        bufWriter.write(reportPage.getLeftIndent());
        for(Column col : reportColumns){
            bufWriter.write( col.getTitle() );
            bufWriter.write( reportPage.getColumnDlmtr() );
        }
    }
}
