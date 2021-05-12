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

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        return ok(views.html.index.render(
            "投稿一覧",
            repo.list(),
            postform,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result show(int id) {
        return ok(views.html.show.render(
            "Show Micropost",
            repo.get(id),
            id
        ));
    }

    public Result create(Http.Request request) {
        MicropostEntity micropost = formFactory.form(MicropostEntity.class).bindFromRequest(request).get();
        repo.add(micropost);
        return redirect(routes.HomeController.index());
    }

    public Result edit(int id, Http.Request request) {
        MicropostEntity micropost = repo.get(id);
        PostForm form = new PostForm(id);
        form.setName(micropost.name);
        form.setTitle(micropost.title);
        form.setMessage(micropost.message);
        form.setLink(micropost.link);
        form.setDeletekey(micropost.deletekey);

        Form<PostForm> formdata = postform.fill(form);
        return ok(views.html.edit.render(
            "投稿の編集",
            formdata,
            id,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result update(int id, Http.Request request) {
        PostForm form = formFactory.form(PostForm.class).bindFromRequest(request).get();
        MicropostEntity post = new MicropostEntity(id, form.getName(), form.getTitle(), form.getMessage(), form.getLink(), form.getDeletekey());
        System.out.println(form.getDeletekey());
        repo.update(post);
        return redirect(routes.HomeController.index());
    }

    public Result delete(int id, Http.Request request) {
        return ok(views.html.delete.render(
            "投稿の削除",
            repo.get(id),
            id,
            postform,
            request,
            messagesApi.preferred(request)
        ));
    }

    public Result remove(int id, Http.Request request) {
        MicropostEntity post = repo.get(id);
        PostForm form = formFactory.form(PostForm.class).bindFromRequest(request).get();
        
        System.out.println(request);
        

        if(form.getDeletekey().equals(post.deletekey)){
            repo.delete(id);
            return redirect(routes.HomeController.index());
        }
        return redirect(routes.HomeController.delete(post.id));
    }

}
