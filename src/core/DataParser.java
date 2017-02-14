package core;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс отвечает за извлечение исходных данных из .TSV файлов.
 */
public class DataParser {
    private BufferedReader bufReader;
    List<String> reportEntries;


    public DataParser(){

    }

    public DataParser(String rawDataFile, String encoding){
        try {
            bufReader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(rawDataFile), Charset.forName(encoding) )
                            );
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void parseData(){
        String entry;
        reportEntries = new ArrayList<>();

        try {
            while( (entry = bufReader.readLine()) != null ){
                reportEntries.add(entry);
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getReportEntries(){
        return reportEntries;
    }
}
