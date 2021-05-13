package controllers;

import java.util.Date;
import java.sql.Timestamp;
import java.util.*;
import play.data.validation.Constraints.*;

public class PostForm {
    protected int id;
    // @Required(message="名前は必須項目です")
    // @MaxLength(value=10, message = "入力できるのは10文字までです")
    protected String name;
    // @Required(message="タイトルは必須項目です")
    // @MaxLength(value=30, message="入力できるのは30文字までです")
    protected String title;
    // @Required(message="メッセージは必須項目です")
    // @MaxLength(value=200, message="入力できるのは200文字までです")
    protected String message;
    protected String link;
    // @Required(message= "削除キーは必須項目です")
    // @MaxLength(value=10, message = "入力できるのは10文字までです")
    protected String deletekey;

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

    public void setDeletekey(String deletekey) {
        this.deletekey = deletekey;
    }

    public String getDeletekey() {
        return deletekey;
    }

}
