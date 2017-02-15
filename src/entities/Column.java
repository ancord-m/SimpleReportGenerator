package entities;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Класс Column служит для представления заголовка произвольной колонки.
 */
public class Column {
    private int width;
    private Queue<Character> title;
    private List<Queue<Character>> entries;

    public Column(){

    }

    public Column(String title, int width){
        this.title = new ArrayDeque<>();
        for (int i = 0; i < title.length(); i++){
            this.title.add(title.charAt(i));
        }
        this.width = width;
        this.entries = new ArrayList<>();
    }

    public Queue<Character> getTitle() {
        return title;
    }

    public List<Queue<Character>> getEntries(){
        return entries;
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
