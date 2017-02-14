package entities;

/**
 * Класс Column служит для представления заголовка произвольной колонки.
 */
public class Column {
    private String title;
    private int width;

    public Column(){

    }

    public Column(String title, int width){
        this.title = title;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

   public int getWidth() {
        return width;
    }


    @Override
    public String toString() {
        return "Column{" +
                "title='" + title + '\'' +
                ", width=" + width +
                '}';
    }
}
