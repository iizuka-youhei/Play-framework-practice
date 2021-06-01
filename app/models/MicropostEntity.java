package models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.*;
import play.data.validation.Constraints.*;
import io.ebean.*;
// import jdk.jfr.Registered;

import java.text.SimpleDateFormat;


@Entity
@Table(name = "micropost")
public class MicropostEntity extends Model{
    @Id
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    public UserEntity user;

    @Required(message="タイトルは必須項目です")
    @MaxLength(value=30, message="入力できるのは30文字までです")
    private String title;

    @Required(message="メッセージは必須項目です")
    @MaxLength(value=200, message="入力できるのは200文字までです")
    private String message;

    private String link;
    private Date created_at;
    private Date updated_at;

    public MicropostEntity() {
        super();
        this.created_at = new Timestamp(new Date().getTime());
        this.updated_at = new Timestamp(new Date().getTime());
    }

    public MicropostEntity(int id, UserEntity user, String title, String message, String link) {
        super();
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.link = link;
        this.updated_at = new Timestamp(new Date().getTime());
    }

    public int getId() {
        return id;
    }

    // public void setUser(UserEntity user) {
    //     this.user = user;
    // }

    // public UserEntity getUser() {
    //     return user;
    // }

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

    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd (E) HH:mm:ss");
        return df.format(this.updated_at);
    }
}
