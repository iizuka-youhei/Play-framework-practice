package controllers;

import models.*;

import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.inject.*;
import play.mvc.*;
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

    @With(BeforeAction.class)
    public Result index(Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);

        return ok(views.html.index.render(
            "投稿一覧",
            repo.list(),
            postform,
            loginUser,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result show(int id) {
        return ok(views.html.show.render(
            "投稿の表示",
            repo.get(id),
            id
        ));
    }

    @With(BeforeAction.class)
    public Result create(Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        if(loginUser == null) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        Form form = formFactory.form(MicropostEntity.class);
        try {
            MicropostEntity micropost = (MicropostEntity)form.bindFromRequest(request).get();
            micropost.user = loginUser;
            repo.add(micropost);
            return redirect(routes.HomeController.index());
        } catch(IllegalStateException e) {
            return badRequest(views.html.index.render(
                "投稿一覧",
                repo.list(),
                form.bindFromRequest(request),
                loginUser,
                request,
                messagesApi.preferred(request)
            ));

        }
    }

    @With(BeforeAction.class)
    public Result edit(int id, Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        MicropostEntity micropost = repo.get(id);
        if(loginUser == null || micropost.user.id != loginUser.id) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        PostForm form = new PostForm(id);
        form.setTitle(micropost.title);
        form.setMessage(micropost.message);
        form.setLink(micropost.link);

        Form<PostForm> formdata = postform.fill(form);
        return ok(views.html.edit.render(
            "投稿の編集",
            formdata,
            id,
            request,
            messagesApi.preferred(request)
        ));
    }

    @With(BeforeAction.class)
    public Result update(int id, Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        MicropostEntity microPost = repo.get(id);
        if(loginUser == null || microPost.user.id != loginUser.id) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        Form form = formFactory.form(MicropostEntity.class);
        try {
            MicropostEntity micropost = (MicropostEntity)form.bindFromRequest(request).get();
            MicropostEntity post = new MicropostEntity(id, loginUser, micropost.title, micropost.message, micropost.link);
            repo.update(post);
            return redirect(routes.HomeController.index());
        } catch(IllegalStateException e) {
            return ok(views.html.edit.render(
                "投稿の編集",
                form.bindFromRequest(request),
                id,
                request,
                messagesApi.preferred(request)
            ));
        }
    }

    @With(BeforeAction.class)
    public Result delete(int id, Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        MicropostEntity post = repo.get(id);
        if(loginUser == null || post.user.id != loginUser.id) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        return ok(views.html.delete.render(
            "投稿の削除",
            repo.get(id),
            id,
            postform,
            request,
            messagesApi.preferred(request)
        ));
    }

    @With(BeforeAction.class)
    public Result remove(int id, Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        MicropostEntity post = repo.get(id);
        if(loginUser != null && post.user.id == loginUser.id) {
            repo.delete(post);
        }
        return redirect(routes.HomeController.index());

    }

}
