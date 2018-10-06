package cat.enricprats.mybooks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookItem {
    private int id;
    private String title;
    private String author;
    private String publication_date;
    private String description;
    private String url_image;

    public BookItem() {
    }

    public BookItem(int id, String title, String author, String publication_date, String description, String url_image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publication_date = publication_date;
        this.description = description;
        this.url_image = url_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
