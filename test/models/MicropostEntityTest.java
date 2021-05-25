package controllers;

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

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
import static play.test.Helpers.route;
import play.api.test.CSRFTokenHelper;


public class MicropostEntityTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void titleが長すぎると掲示板に投稿できない() {
        String title = "この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています";
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", title, "message", "メッセージです。", "link", ""))
                .uri("/create");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void titleが未記入だと掲示板に投稿できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "", "message", "メッセージです。", "link", ""))
                .uri("/create");

        request = CSRFTokenHelper.addCSRFToken(request);

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void messageが長すぎると掲示板に投稿できない() {
        String message = "この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れて";
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", message, "link", ""))
                .uri("/create");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void messageが未記入だと掲示板に投稿ができない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", "", "link", ""))
                .uri("/create");

        request = CSRFTokenHelper.addCSRFToken(request);

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void titleもmessageも未記入だと掲示板に投稿ができない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "", "message", "", "link", ""))
                .uri("/create");

        request = CSRFTokenHelper.addCSRFToken(request);

        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void titleが長すぎると投稿の編集に失敗する() {
        String title = "この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています";
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", title, "message", "編集後のメッセージです。", "link", ""))
                .uri("/update/1");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        MicropostEntity post = Ebean.find(MicropostEntity.class, 1);
        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(OK, result.status());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }

    @Test
    public void titleが未記入だと投稿の編集に失敗する() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "", "message", "編集後のメッセージです。", "link", ""))
                .uri("/update/1");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        MicropostEntity post = Ebean.find(MicropostEntity.class, 1);
        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(OK, result.status());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }

    @Test
    public void messageが長すぎると投稿の編集に失敗する() {
        String message = "この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れています。この文章はダミーです。文字の大きさ、量、字間、行間等を確認するために入れて";
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "編集後のタイトル", "message", message, "link", ""))
                .uri("/update/1");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        MicropostEntity post = Ebean.find(MicropostEntity.class, 1);
        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(OK, result.status());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }

    @Test
    public void messageが未記入だと投稿の編集に失敗する() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "編集後のタイトル", "message", "", "link", ""))
                .uri("/update/1");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        MicropostEntity post = Ebean.find(MicropostEntity.class, 1);
        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(OK, result.status());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }

    @Test
    public void titleもmessageも未記入だと投稿の編集に失敗する() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "", "message", "", "link", ""))
                .uri("/update/1");
        
        request = CSRFTokenHelper.addCSRFToken(request);

        MicropostEntity post = Ebean.find(MicropostEntity.class, 1);
        request.session("login", Ebean.find(UserEntity.class, 1).getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(OK, result.status());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }
}
