package controllers;

import models.MicropostRepository;
import models.PostForm;
import models.UserEntity;
import models.MicropostEntity;
import models.SearchForm;

import java.lang.ProcessBuilder.Redirect;
import javax.inject.Inject;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private final Form<PostForm> postform;
    private final Form<SearchForm> searchform;
    private final FormFactory formFactory;
    private final MicropostRepository repo;
    private MessagesApi messagesApi;

    @Inject
    public HomeController(FormFactory formFactory, MicropostRepository micropostRepository, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.postform = formFactory.form(PostForm.class);
        this.searchform = formFactory.form(SearchForm.class);
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
            searchform,
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
                searchform,
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
        if(loginUser == null || micropost.user.getId() != loginUser.getId()) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        PostForm form = new PostForm(id);
        form.setTitle(micropost.getTitle());
        form.setMessage(micropost.getMessage());
        form.setLink(micropost.getLink());

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
        if(loginUser == null || microPost.user.getId() != loginUser.getId()) {
            return redirect(routes.HomeController.index()); // 不適切なユーザの場合
        }
        Form form = formFactory.form(MicropostEntity.class);
        try {
            MicropostEntity micropost = (MicropostEntity)form.bindFromRequest(request).get();
            MicropostEntity post = new MicropostEntity(id, loginUser, micropost.getTitle(), micropost.getMessage(), micropost.getLink());
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
        if(loginUser == null || post.user.getId() != loginUser.getId()) {
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
        if(loginUser != null && post.user.getId() == loginUser.getId()) {
            repo.delete(post);
        }
        return redirect(routes.HomeController.index());

    }

    @With(BeforeAction.class)
    public Result search(Http.Request request) {
        UserEntity loginUser = request.attrs().get(Attrs.USER);
        Form form = formFactory.form(SearchForm.class);
        try {
            SearchForm sform = (SearchForm)form.bindFromRequest(request).get();
            String keyword = sform.getKeyword();

            return ok(views.html.index.render(
                keyword + "での検索結果",
                repo.find(keyword),
                postform,
                searchform.fill(sform),
                loginUser,
                request,
                messagesApi.preferred(request)
            ));
        } catch(IllegalStateException e) {
            return badRequest(views.html.index.render(
                "投稿一覧",
                repo.list(),
                postform,
                form.bindFromRequest(request),
                loginUser,
                request,
                messagesApi.preferred(request)
            ));
        }

    }

}
