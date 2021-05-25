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


public class UserLoginFormTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void emailが未入力の場合はバリデーションエラー() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("email", "", "password", "sato456"))
                .uri("/logincheck");

        request = CSRFTokenHelper.addCSRFToken(request);

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void passwordが未入力の場合はバリデーションエラー() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("email", "sato123@gmail.com", "password", ""))
                .uri("/logincheck");

        request = CSRFTokenHelper.addCSRFToken(request);

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void emailもpasswordも未入力の場合はバリデーションエラー() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("email", "", "password", ""))
                .uri("/logincheck");

        request = CSRFTokenHelper.addCSRFToken(request);

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

}
