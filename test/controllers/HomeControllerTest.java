package controllers;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
// import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
import static play.test.Helpers.route;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    // @Test
    // public void testIndex() {
    //     Http.RequestBuilder request = new Http.RequestBuilder()
    //             .method(GET)
    //             .uri("/"); // appの(/)にGETリクエストを投げる

    //     Result result = route(app, request); // サーバからレスポンスをもらう
    //     assertEquals(OK, result.status()); // Httpレスポンスのstatusが200であることを確認。
    // }

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

    // @Test
    // public void 自分の投稿ならば編集ページを表示できる() {
    //     Http.RequestBuilder request = Helpers.fakeRequest()
    //             .method(GET)
    //             .uri("/edit/1");
        
    //     request.session("login", Ebean.find(UserEntity.class, 1)); 
        
    //     Result result = route(app, request);
    //     assertEquals(OK, result.status());
    // }

    @Test
    public void ログインしていないと投稿できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("title", "タイトルです", "message", "メッセージです。", "link", ""))
                .uri("/create");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void 自分の投稿でないと編集ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/edit/1000000");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void 自分の投稿でないと削除ページを表示できない() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/delete/1000000");
        
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
        assertEquals("/", result.redirectLocation().get());
    }

    @Test
    public void testBadRoute() {
        Http.RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/xx/Kiwi");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

}
