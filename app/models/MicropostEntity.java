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
    @Required(message="名前は必須項目です")
    @MaxLength(value=10, message = "入力できるのは10文字までです")
    public String name;
    @Required(message="タイトルは必須項目です")
    @MaxLength(value=30, message="入力できるのは30文字までです")
    public String title;
    @Required(message="メッセージは必須項目です")
    @MaxLength(value=200, message="入力できるのは200文字までです")
    public String message;
    public String link;
    @Required(message= "削除キーは必須項目です")
    @MaxLength(value=10, message = "入力できるのは10文字までです")
    public String deletekey;
    public Date created_at;
    public Date updated_at;

    public MicropostEntity() {
        super();
        this.created_at = new Timestamp(new Date().getTime());
        this.updated_at = new Timestamp(new Date().getTime());
    }

    public MicropostEntity(int id, String name, String title, String message, String link, String deletekey) {
        super();
        this.id = id;
        this.name = name;
        this.title = title;
        this.message = message;
        this.link = link;
        this.deletekey = deletekey;
        // this.created_at = new Timestamp(new Date().getTime());
        this.updated_at = new Timestamp(new Date().getTime());
    }

    @Override
    public String toString() {
        return id + ": " + name + " [" + message +"]" + created_at;
    }
}
