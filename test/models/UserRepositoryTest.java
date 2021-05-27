package models;

import models.UserRepository;
import models.UserEntity;
import models.MicropostEntity;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.Helpers;
import io.ebean.*;
import javax.inject.Inject;


import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
import static play.test.Helpers.route;
import play.api.test.CSRFTokenHelper;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest extends WithApplication {
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    private final UserRepository repo = new UserRepository();

    // @Inject
    // public UserRepositoryTest(UserRepository userRepository) {
    //     this.repo = userRepository;
    // }

    @Test
    public void 全ユーザーを取得できるか() {
        List<UserEntity> userlist = repo.list();
        assertEquals(3, userlist.size());
    }

}