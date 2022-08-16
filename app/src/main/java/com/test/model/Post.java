package com.test.model;

public class Post {
    private String id, author, description, link;

    public Post(String id, String author, String description, String link) {
        this.id = id;
        this.author = author;
        this.link = link;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
