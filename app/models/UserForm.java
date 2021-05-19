package controllers;

import java.util.Date;
import java.sql.Timestamp;
import java.util.*;
import play.data.validation.Constraints.*;

public class UserForm {
    protected int id;
    protected String name;
    protected String email;
    protected String password;

    public UserForm() {
        super();
    }

    public UserForm(int id) {
        super();
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
