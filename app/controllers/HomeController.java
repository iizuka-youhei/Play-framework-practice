package controllers;

import models.*;

import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.inject.*;
import play.mvc.*; // 初めからあった
import play.data.*;
import io.ebean.*;
import play.i18n.MessagesApi;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private final Form<PostForm> postform;
    private final FormFactory formFactory;
    private final MicropostRepository repo;
    private MessagesApi messagesApi;

    @Inject
    public HomeController(FormFactory formFactory, MicropostRepository micropostRepository, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.postform = formFactory.form(PostForm.class);
        this.repo = micropostRepository;
        this.messagesApi = messagesApi;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render(
            "Micropost List.",
            repo.list()
            // Ebean.find(MicropostEntity.class).findList() // Ebeanクラスを利用する方法
        ));
    }

    public Result show(int id) {
        return ok(views.html.show.render(
            "Show Micropost",
            repo.get(id),
            // Ebean.find(MicropostEntity.class, id), // Ebeanクラスを利用する方法
            id
        ));
    }

    public Result add(Http.Request request) {
        return ok(views.html.add.render(
            "フォーム",
            postform,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result create(Http.Request request) {
        MicropostEntity micropost = formFactory.form(MicropostEntity.class).bindFromRequest(request).get();
        repo.add(micropost);
        return redirect(routes.HomeController.index());
    }

    public Result hello(String name) {
        return ok(views.html.hello.render(name));
    }
}
