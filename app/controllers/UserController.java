package controllers;

import models.*;

import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.inject.*;
import play.mvc.*;
import play.data.*;
import io.ebean.*;
import play.i18n.MessagesApi;

public class UserController extends Controller{
    private final Form<UserForm> userform;
    private final FormFactory formFactory;
    private final UserRepository repo;
    private MessagesApi messagesApi;

    @Inject
    public UserController(FormFactory formFactory, UserRepository userRepository, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.userform = formFactory.form(UserForm.class);
        this.repo = userRepository;
        this.messagesApi = messagesApi;
    }

    public Result login(Http.Request request) {
        return ok(views.html.login.render(
            "ログイン",
            userform,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result logincheck(Http.Request request) {
        UserForm form = formFactory.form(UserForm.class).bindFromRequest(request).get();
        List<UserEntity> users = repo.get("email", form.getEmail());
        // if(form.getPassword().equals(users.get(0).password)) {
        //     return redirect(routes.HomeController.index());
        // }
        return redirect(routes.HomeController.show(1));
    }
}
