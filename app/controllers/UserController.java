package controllers;

import models.UserLoginForm;
import models.UserRepository;
import models.UserEntity;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import javax.inject.Inject;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import io.ebean.*;
import play.i18n.MessagesApi;

public class UserController extends Controller{
    private final Form<UserLoginForm> userLoginForm;
    private final FormFactory formFactory;
    private final UserRepository repo;
    private MessagesApi messagesApi;

    @Inject
    public UserController(FormFactory formFactory, UserRepository userRepository, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.userLoginForm = formFactory.form(UserLoginForm.class);
        this.repo = userRepository;
        this.messagesApi = messagesApi;
    }

    @With(BeforeAction.class)
    public Result login(Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);

        return ok(views.html.login.render(
            "ログイン",
            userLoginForm,
            loginUser,
            request,
            messagesApi.preferred(request)
        ));
    }

    @With(BeforeAction.class)
    public Result logincheck(Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        Form<UserLoginForm> form = userLoginForm.bindFromRequest(request);
        
        if (form.hasErrors()){
            return badRequest(views.html.login.render("ログイン", userLoginForm, loginUser, request, messagesApi.preferred(request)));
        } else {
            UserLoginForm data = form.get();
            List<UserEntity> user = repo.get("email", data.getEmail());
            
            try {
                if(data.getPassword().equals(user.get(0).getPassword())) {
                    return redirect(routes.HomeController.index()).addingToSession(request, "login", user.get(0).getEmail());
                }
                return redirect(routes.UserController.login());
            }
            catch (IndexOutOfBoundsException e){
                return redirect(routes.UserController.login());
            }
        }

    }

    public Result logout(Http.Request request) {
        return redirect(routes.HomeController.index()).removingFromSession(request, "login");
    }
}
