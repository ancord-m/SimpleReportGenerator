package entities;

/**
 * Статический класс Page хранит и обеспечивает доступ к параметрам страницы.
 */
public class Page {
    private int pageWidth;
    private int pageHeight;
    private StringBuilder horizontalBar;
    private final String PAGE_DELIMITER = "~";
    private final String COLUMN_DLMTR = "|";
    private final String LEFT_INDENT = "| ";
    private final String RIGHT_INDENT = " |";

    public Page(){

    }

    public Page(int pageWidth, int pageHeight){
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;

        horizontalBar = new StringBuilder();
        while ( 0 < pageWidth-- ){
            horizontalBar.append("-");
        }
        horizontalBar.append(System.getProperty("line.separator"));
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

    public String getNewPage(){
        return System.getProperty("line.separator")
                    + PAGE_DELIMITER
                        + System.getProperty("line.separator");
    }
}
