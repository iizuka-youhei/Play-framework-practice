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
@Table(name = "user")
public class UserEntity extends Model{
    @Id
    public Integer id;
    public String name;
    public String email;
    public String password;
    public Date created_at;
    public Date updated_at;

    public UserEntity() {
        super();
        this.created_at = new Timestamp(new Date().getTime());
        this.updated_at = new Timestamp(new Date().getTime());
    }

    public UserEntity(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

        this.updated_at = new Timestamp(new Date().getTime());
    }
}
