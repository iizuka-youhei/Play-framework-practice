package models;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.*;
import play.data.validation.Constraints.*;
import io.ebean.*;
import jdk.jfr.Registered;

import java.text.SimpleDateFormat;

@Entity
@Table(name = "user")
public class UserEntity extends Model{
    @Id
    private Integer id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    public List<MicropostEntity> microposts = new ArrayList<MicropostEntity>();

    private Date created_at;
    private Date updated_at;

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

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd (E) HH:mm:ss");
        return df.format(this.updated_at);
    }
}
