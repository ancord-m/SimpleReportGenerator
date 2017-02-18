package entities;

/**
 * Статический класс Page хранит и обеспечивает доступ к параметрам страницы.
 */
public class Page {
    private int pageWidth;
    private int pageHeight;
    private StringBuilder horizontalBar;
    private String lineSeparator;
    private final String PAGE_DELIMITER = "~";
    private final String COLUMN_DLMTR = " | ";
    private final String LEFT_INDENT = "| ";
    private final String RIGHT_INDENT = " |";


    public Page(){

    }

    public Page(int pageWidth, int pageHeight){
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;

        lineSeparator = System.getProperty("line.separator");
        horizontalBar = new StringBuilder();
        while ( 0 < pageWidth-- ){
            horizontalBar.append("-");
        }
        horizontalBar.append(lineSeparator);
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public String getHorizontalBar(){
        return horizontalBar.toString();
    }

    public String getColumnDlmtr(){
        return COLUMN_DLMTR;
    }

    public String getLeftIndent(){
        return LEFT_INDENT;
    }

    public String getRightIndent(){
        return RIGHT_INDENT + System.getProperty("line.separator");
    }

    public String getLineSeparator(){
        return lineSeparator;
    }

    public String getNewPage(){
        return PAGE_DELIMITER
                        + lineSeparator;
    }
}
