package controllers;

import models.UserEntity;
import models.MicropostEntity;

import com.google.common.collect.ImmutableMap;
import org.junit.*;
// import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.Helpers;
import io.ebean.*;

import play.db.Database;
import play.db.Databases;
import play.db.evolutions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
// import static play.test.Helpers.route;

import java.util.ArrayList;
import java.util.List;

public class HomeControllerTest extends WithApplication {

    Database database;
    private final UserRepository repo;

    @Inject
    public HomeControllerTest(UserRepository micropostRepository) {
        this.repo = micropostRepository;
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void setupDatabase() {
        database = Databases.inMemory();
        Evolutions.applyEvolutions(
            database,
            Evolutions.fromClassLoader(getClass().getClassLoader(), "testdatabase/"));
    }

    @After
    public void shutdownDatabase() {
        Evolutions.cleanupEvolutions(database);
        database.shutdown();
    }

    public UserEntity getLoginUser() {
        Connection connection = database.getConnection();
        UserEntity loginUser = null;
        try {
            ResultSet rs = connection.prepareStatement("select * from user where id = 1").executeQuery();
            
        
            while(rs.next()){
                loginUser = new UserEntity(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            }
        } catch(SQLException e) {}

        // loginUser = Ebean.find(UserEntity.class, 1);

        return loginUser;
    }

    public List<MicropostEntity> getLosinUserPosts() {
        Connection connection = database.getConnection();
        List<MicropostEntity> microposts = null;
        try {
            ResultSet rs = connection.prepareStatement("select * from micropost where user_id = 1").executeQuery();
            
        
            while(rs.next()){
                microposts.add(new MicropostEntity(rs.getInt("id"), getLoginUser(), rs.getString("title"), rs.getString("message"), rs.getString("link")));
            }
        } catch(SQLException e) {}

        return microposts;
    }

    @Test
    public void testDatabase() throws Exception {
        Connection connection = database.getConnection();
        // connection.prepareStatement("insert into user values (default, 'taro', 'taro@gmail.com', 'taro123', default, default);").execute();

        // assertTrue(
        //     connection.prepareStatement("select * from test where id = 10").executeQuery().next()
        // );
        ResultSet rs = connection.prepareStatement("select * from user where id = 1").executeQuery();
        
        while(rs.next()){
            assertEquals("taro", rs.getString("name"));
        }
        
    }


    @Test
    public void トップページが表示できるか() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void showページが表示できるか() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/show/1");
        
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void 存在しないIDを入力したらリダイレクトする() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/show/1000000");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void IDに文字列を入れたらBad_Requestを返す() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/show/abc");
        
        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void 掲示板に投稿できるか() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", "メッセージです。", "link", ""))
                .uri("/create");

        request.session("login", getLoginUser().getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
        assertEquals(post_num + 1, Ebean.find(MicropostEntity.class).findList().size());
    }

    @Test
    public void ログインしていないと掲示板に投稿できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", "メッセージです。", "link", ""))
                .uri("/create");

        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
    }

    @Test
    public void 自分の投稿ならば編集ページを表示できる() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/edit/1");
        
        request.session("login", getLoginUser().getEmail()); 
        
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void 自分の投稿でないと編集ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/edit/2");
        
        request.session("login", getLoginUser().getEmail()); 
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }


    @Test
    public void ログインしていないと編集ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/edit/1");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void 自分の投稿なら編集ができる() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "編集後のタイトルです", "message", "編集後のメッセージです", "link", ""))
                .uri("/update/1");

        request.session("login", getLoginUser().getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 1);

        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals("編集後のタイトルです", updated_post.getTitle());
        assertEquals("編集後のメッセージです", updated_post.getMessage());
    }

    @Test
    public void 他人の投稿は編集できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", "メッセージです。", "link", ""))
                .uri("/update/2");

        request.session("login", getLoginUser().getEmail());
        int post_num = Ebean.find(MicropostEntity.class).findList().size();
        MicropostEntity post = Ebean.find(MicropostEntity.class, 2);
        
        Result result = route(app, request);
        MicropostEntity updated_post = Ebean.find(MicropostEntity.class, 2);

        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
        assertEquals(post_num, Ebean.find(MicropostEntity.class).findList().size());
        assertEquals(post.getTitle(), updated_post.getTitle());
        assertEquals(post.getMessage(), updated_post.getMessage());
    }

    @Test
    public void 自分の投稿ならば削除ページを表示できる() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/delete/1");

        request.session("login", getLoginUser().getEmail()); 
        
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void 自分の投稿でないと削除ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/delete/2");

        request.session("login", getLoginUser().getEmail()); 
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void ログインしていないと削除ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/delete/1");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void 自分の投稿は削除できる() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .uri("/remove/1");
        
        request.session("login", getLoginUser().getEmail());

        Result result = route(app, request);
        assertEquals("/", result.redirectLocation().get());
        assertEquals(null, Ebean.find(MicropostEntity.class, 1));
    }

    @Test
    public void 他人の投稿は削除できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .uri("/remove/2");
        
        request.session("login", getLoginUser().getEmail()); 
        MicropostEntity post = Ebean.find(MicropostEntity.class, 2);

        Result result = route(app, request);
        assertEquals("/", result.redirectLocation().get());
        assertEquals(post, Ebean.find(MicropostEntity.class, 2));
    }

    @Test
    public void testBadRoute() {
        Http.RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/xx/Kiwi");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

}
