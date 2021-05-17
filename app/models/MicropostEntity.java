package models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.*;
import play.data.validation.Constraints.*;
import io.ebean.*;
import jdk.jfr.Registered;

@Entity
@Table(name = "micropost")
public class MicropostEntity extends Model{
    @Id
    public Integer id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    public UserEntity user;

    @Required(message="タイトルは必須項目です")
    @MaxLength(value=30, message="入力できるのは30文字までです")
    public String title;

    @Required(message="メッセージは必須項目です")
    @MaxLength(value=200, message="入力できるのは200文字までです")
    public String message;

    public String link;
    public Date created_at;
    public Date updated_at;

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

}
