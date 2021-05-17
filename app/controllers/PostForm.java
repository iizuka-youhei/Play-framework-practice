package controllers;

import java.util.Date;
import java.sql.Timestamp;
import java.util.*;
import play.data.validation.Constraints.*;

public class PostForm {
    protected int id;
    protected String title;
    protected String message;
    protected String link;

    public PostForm() {
        super();
    }

    public PostForm(int id) {
        super();
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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

}
