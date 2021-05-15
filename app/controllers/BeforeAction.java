package controllers;

import models.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.inject.*;
import play.mvc.*;
import play.data.*;
import io.ebean.*;
import play.i18n.MessagesApi;

import java.util.concurrent.CompletionStage;


public class BeforeAction extends play.mvc.Action.Simple{
    @Override
    public CompletionStage<Result> call(Http.Request request) {
        UserEntity loginUser = null;
        if(request.session().get("login").isPresent()) {
            System.out.println("login now");
            System.out.println(request.session().get("login").get());
            loginUser = Ebean.find(UserEntity.class).where().eq("email", request.session().get("login").get()).findOne();
        } else {
            System.out.println("logout now");
        }
        return delegate.call(request.addAttr(Attrs.USER, loginUser));
    }
}
