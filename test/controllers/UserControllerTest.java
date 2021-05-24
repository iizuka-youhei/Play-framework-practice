package controllers;

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
import static play.test.Helpers.GET;
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

}
