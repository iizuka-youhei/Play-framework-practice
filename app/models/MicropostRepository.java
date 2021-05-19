package models;

import java.util.List;
import models.MicropostEntity;
import io.ebean.*;

public class MicropostRepository {
    public EbeanServer ebean;
    public static Finder<Integer, MicropostEntity> find = new Finder<Integer, MicropostEntity>(MicropostEntity.class);

    public MicropostRepository() {
        this.ebean = Ebean.getDefaultServer();
    }

    public List<MicropostEntity> list() {
        return find.all();
    }

    public MicropostEntity get(int id) {
        return find.byId(id);
    }

    public void add(MicropostEntity micropost) {
        micropost.save();
    }

    public void update(MicropostEntity micropost) {
        micropost.update();
    }

    public void delete(int id) {
        find.byId(id).delete();
    }

    public void delete(MicropostEntity micropost) {
        micropost.delete();
    }

}
