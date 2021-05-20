package models;

import models.UserEntity;
import java.util.List;

import io.ebean.*;

public class UserRepository {
    public EbeanServer ebean;
    public static Finder<Integer, UserEntity> find = new Finder<Integer, UserEntity>(UserEntity.class);

    public UserRepository() {
        this.ebean = Ebean.getDefaultServer();
    }

    public List<UserEntity> list() {
        return find.all();
    }

    public UserEntity get(int id) {
        return find.byId(id);
    }

    public List<UserEntity> get(String column, String keyword) {
        return find.query().where().eq(column, keyword).findList();
    }

    public void add(UserEntity user) {
        user.save();
    }

    public void update(UserEntity user) {
        user.update();
    }

    public void delete(int id) {
        find.byId(id).delete();
    }

    public void delete(UserEntity user) {
        user.delete();
    }

}
