package core;

import entities.Column;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс отвечает за извлечение исходных данных из .TSV файлов.
 */
public class DataParser {
    private BufferedReader bufReader;
    List<Column> reportColumns;


    public DataParser(){

    }

    /**
     *
     * @param rawDataFile
     * @param reportColumns
     * @param encoding
     */
    public DataParser(String rawDataFile, List<Column> reportColumns, String encoding){
        try {
            bufReader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(rawDataFile), Charset.forName(encoding) )
                            );
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        this.reportColumns = reportColumns;
    }

    public void parseData(){
        String rawDataLine;
        String[] rawDataSublines;

        try {
            for(int lineCounter = 0; (rawDataLine = bufReader.readLine()) != null; lineCounter++){
                rawDataSublines = rawDataLine.split("\\t");
                for(int i = 0; i < rawDataSublines.length; i++){
                    reportColumns.get(i)
                            .getEntries().add(
                                new ArrayDeque<>()
                             );
                    for(int j = 0; j < rawDataSublines[i].length(); j++) {
                        reportColumns.get(i)
                                .getEntries().get(lineCounter)
                                    .add(
                                            rawDataSublines[i].charAt(j)
                                    );
                    }
                }
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Column> getReportEntries(){
        return reportColumns;
    }
}
