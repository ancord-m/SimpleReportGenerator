package core;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
    private int linesInHeader;
    private int numOfLinesPrinted;
    private int numOfEntries;
    private StringBuilder reportHeader;
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
        numOfEntries = dataParser.parseData();
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
        boolean allColNotEmpty;

        try{
            writePageHeader();
            for(int i = 0; i < numOfEntries; i++) {
                allColNotEmpty = true;
                while(allColNotEmpty){
                    allColNotEmpty = false;
                    bufWriter.write(reportPage.getLeftIndent());
                    for (Column col : reportColumns) {
                        writePartOfColumnEntry(col.getWidth(), col.getEntries().get(i));
                        allColNotEmpty |= !col.getEntries().get(i).isEmpty();
                    }
                    bufWriter.write(reportPage.getLineSeparator());
                    if(!allColNotEmpty){
                        bufWriter.write(reportPage.getHorizontalBar());
                        numOfLinesPrinted++;
                    }
                    bufWriter.flush();
                    numOfLinesPrinted++;
                    if(linesInHeader + numOfLinesPrinted == reportPage.getPageHeight()){
                        numOfLinesPrinted = 0;
                        bufWriter.write(reportPage.getNewPage());
                        bufWriter.flush();
                        writePageHeader();

                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }
    private void writePageHeader() throws IOException{
        if(reportHeader == null){
            reportHeader = new StringBuilder();
            constructHeader();
        }
        bufWriter.write(reportHeader.toString());
        bufWriter.flush();
    }

    /**
     * Метод формирует заголовок страницы в виде объекта StringBuilder с целью многократного использования.
     * Нужные данные, а именно, заголовок и его ширина, храняться в объекте Column.
     * Здесь они извлекаются, обрамляются необходимыми разделителями и завершаются
     * горизонтальной чертой, отделяющей строки записей на страницах.
     * @throws IOException
     */
    private void constructHeader() throws IOException{
        //TODO RightIndent тащит за собой пробел!
        int charCount = 0;
        boolean allColNotEmpty = true;

        while(allColNotEmpty){
            allColNotEmpty = false;
            reportHeader.append(reportPage.getLeftIndent());
            for(Column col : reportColumns){
                charCount = 0;

                while( (charCount < col.getWidth() ) & !col.getTitle().isEmpty()){
                    reportHeader.append(col.getTitle().poll());
                    charCount++;
                }

                if(charCount == col.getWidth()){
                    reportHeader.append(reportPage.getColumnDlmtr());
                } else if(col.getTitle().isEmpty()){
                    while(charCount != col.getWidth()){
                        reportHeader.append(" ");
                        charCount++;
                    }
                    reportHeader.append(reportPage.getColumnDlmtr());
                }
                allColNotEmpty |= !col.getTitle().isEmpty();
            }
            reportHeader.append(reportPage.getLineSeparator());
            linesInHeader++;
        }

        reportHeader.append(reportPage.getHorizontalBar());
        linesInHeader++;
    }

    private void writePageEntry(int entryNum) throws IOException{
        boolean allColNotEmpty = true;

        while(allColNotEmpty){
            allColNotEmpty = false;
                bufWriter.write(reportPage.getLeftIndent());
                for (Column col : reportColumns) {
                    writePartOfColumnEntry(col.getWidth(), col.getEntries().get(entryNum));
                    allColNotEmpty |= !col.getEntries().get(entryNum).isEmpty();
                }
                bufWriter.write(reportPage.getLineSeparator());
                numOfLinesPrinted++;
                bufWriter.flush();

              //  bufWriter.write(reportPage.getNewPage());
              //  numOfLinesPrinted = 0;
            }

    //    bufWriter.write(reportPage.getHorizontalBar());
    //    bufWriter.flush();
    }

    /**
     *
     * @param width Ширина в символах, отведенная под заполнение в произвольной колонке.
     * @param columnEntry Часть записи, которую надо поместить в соответствующую колонку.
     */
    private void writePartOfColumnEntry(int width, Queue<Character> columnEntry) throws IOException{
        int charCount = 0;
        while( (charCount < width ) & !columnEntry.isEmpty()){
            bufWriter.write(columnEntry.poll());
            charCount++;
        }

        if(charCount == width){
            bufWriter.write(reportPage.getColumnDlmtr());
        } else if(columnEntry.isEmpty()){
            while(charCount != width){
                bufWriter.write(" ");
                charCount++;
            }
            bufWriter.write(reportPage.getColumnDlmtr());
        }
        bufWriter.flush();
    }

    /*
    private void writePageHeader2() throws IOException{


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
            charCount = 0;

            for (int i = 0; i < 2 & !charQueue1.isEmpty(); i++) {
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
    */
}
