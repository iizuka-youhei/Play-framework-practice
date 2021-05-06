package controllers;

import java.util.*;

public class PostForm {
    protected int id;
    protected String name;
    protected String message;
    protected String link;
    protected String delete_key;

    public PostForm() {
        super();
    }

    public PostForm(int id) {
        super();
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setDeleteKey(String delete_key) {
        this.delete_key = delete_key;
    }

    public String getDeleteKey() {
        return delete_key;
    }

}
