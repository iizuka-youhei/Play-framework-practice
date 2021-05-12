package models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.*;
import io.ebean.*;

@Entity
@Table(name = "micropost")
public class MicropostEntity extends Model{
    @Id
    public Integer id;
    public String name;
    public String title;
    public String message;
    public String link;
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
