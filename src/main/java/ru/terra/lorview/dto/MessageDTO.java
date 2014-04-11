package ru.terra.lorview.dto;

/**
 * Date: 11.04.14
 * Time: 13:26
 */
public class MessageDTO {
    private String title, message, author, date;
    private Integer id;

    public MessageDTO(String title, String message, String author, String date, Integer id) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.date = date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
