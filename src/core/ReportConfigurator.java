package core;

import entities.Column;
import entities.Page;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс извлекает настройки из файла settings.xml
 * На основе извлечённых параметров будут генерироваться страницы отчёта.
 */
public class ReportConfigurator {
    private Page reportPage;
    private List<Column> reportColumns;
    private DocumentBuilder builder;
    private Document xmlSettings;

    public ReportConfigurator(){

    }

    public ReportConfigurator(String settingsFile){
        //TODO что-то сделать с исключениями
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlSettings = builder.parse(settingsFile);
        } catch(ParserConfigurationException e) {
            e.printStackTrace(System.out);
        } catch(SAXException e) {
            e.printStackTrace(System.out);
        } catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void configureReport(){
        Element root = xmlSettings.getDocumentElement();

        NodeList pages = root.getElementsByTagName("page");
        NodeList columns = root.getElementsByTagName("column");

        setPageParams(pages);
        setColumnsParams(columns);
    }

    /**
     *
     * @param pages
     */
    private void setPageParams(NodeList pages){
        int pageWidth = 0;
        int pageHeight = 0;
        NodeList pageParams = pages.item(0).getChildNodes();

        for(int i = 0; i < pageParams.getLength(); i++){
            if(pageParams.item(i) instanceof Element){
                Element param = (Element) pageParams.item(i);
                String value = ( (Text)param.getFirstChild() ).getData().trim();
                if(param.getTagName().equals("width")){
                    pageWidth = Integer.parseInt(value);
                } else if(param.getTagName().equals("height")){
                    pageHeight = Integer.parseInt(value);
                }
            }
        }

        reportPage = new Page(pageWidth, pageHeight);
    }

    /**
     *
     * @param columns
     */
    private void setColumnsParams(NodeList columns){
        String title = "";
        int width = -1;
        reportColumns = new ArrayList<>();

        for(int i = 0; i < columns.getLength(); i++){
            NodeList columnsParams = columns.item(i).getChildNodes();

            for(int j = 0; j < columnsParams.getLength(); j++){
                if(columnsParams.item(j) instanceof Element){
                    Element param = (Element) columnsParams.item(j);
                    String value = ( (Text)param.getFirstChild() ).getData().trim();
                    if(param.getTagName().equals("title")){
                        title = value;
                    } else if(param.getTagName().equals("width")){
                        width = Integer.parseInt(value);
                    }

                    if(!title.isEmpty() & width >= 0){
                        reportColumns.add(new Column(title, width));
                        title = ""; width = -1;
                    }
                }
            }
        }
    }

    public Page getReportPage(){
        return reportPage;
    }

    public List<Column> getReportColumns(){
        return reportColumns;
    }

    public static void main(String[] args) {
        ReportConfigurator rc = new ReportConfigurator();
        rc.configureReport();
    }
}
