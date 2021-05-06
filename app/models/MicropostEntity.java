package models;

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
    public String message;
    public String link;
    public String delete_key;

    public MicropostEntity() {
        super();
    }

    public MicropostEntity(int id, String name, String message, String link, String delete_key) {
        super();
        this.id = id;
        this.name = name;
        this.message = message;
        this.link = link;
        this.delete_key = delete_key;
    }

    @Override
    public String toString() {
        return id + ": " + name + " [" + message +"]";
    }
}
