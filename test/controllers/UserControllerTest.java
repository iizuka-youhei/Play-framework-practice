package controllers;

import models.UserEntity;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.Helpers;
import io.ebean.*;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
import static play.test.Helpers.route;


public class UserControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }


    @Test
    public void loginページが表示できるか() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/login");
        
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void ログイン中はloginページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/login");

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail()); 
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void ログインしたらトップページにリダイレクトしてセッションにemailを保存() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("email", "sato@gmail.com", "password", "sato123"))
                .uri("/logincheck");

        UserEntity user = Ebean.find(UserEntity.class, 2);
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
        assertEquals(user.getEmail(), result.session().get("login").get());
    }

    @Test
    public void 間違ったメールアドレスやパスワードを入力したらloginページへリダイレクトする() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("email", "sato@gmail.com", "password", "sato456"))
                .uri("/logincheck");

        // UserEntity user = Ebean.find(UserEntity.class, 2);
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/login", result.redirectLocation().get());
    }

    @Test
    public void ログアウトしたらトップページにリダイレクトする() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .bodyForm(ImmutableMap.of("email", "sato@gmail.com", "password", "sato123"))
                .uri("/logout");

        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

}
