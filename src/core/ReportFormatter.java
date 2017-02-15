package core;

import entities.Column;
import entities.Page;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

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
        dataParser = new DataParser(rawDataFile, reportColumns, encoding);
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
      /*  List<String> value = dataParser.getReportEntries();

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
        */
    }

    private void writePageHeader() throws IOException{
   //     bufWriter.write(reportPage.getLeftIndent());
        String s = "Experiment";
        String s2 = "Testere";
        Queue<Character> charQueue1 = new ArrayDeque<>();
        Queue<Character> charQueue2 = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++){
            charQueue1.add(s.charAt(i));
        }

        for (int i = 0; i < s2.length(); i++){
            charQueue2.add(s2.charAt(i));
        }

        while(!charQueue1.isEmpty() | !charQueue2.isEmpty()){
            int charCount = 0;

            for (int i = 0; i < 7 & !charQueue1.isEmpty(); i++) {
                bufWriter.write( charQueue1.poll() );
                charCount++;
            }

            if(charCount == 7){
                bufWriter.write(" ");
                charCount = 0;
            } else if(charQueue1.isEmpty()){
                while(charCount != 7+1){
                    bufWriter.write(" ");
                    charCount++;
                }
                charCount = 0;
            }

            for (int i = 0; i < 3 & !charQueue2.isEmpty(); i++) {
                bufWriter.write( charQueue2.poll() );
                charCount++;
            }

            if(charCount == 3){
                bufWriter.write(" ");
                charCount = 0;
            } else if(charQueue1.isEmpty()){
                while(charCount != 3+1){
                    bufWriter.write(" ");
                    charCount++;
                }
                charCount = 0;
            }

            bufWriter.write("\r\n");

            bufWriter.flush();
        }
    }
}
