package controllers;

import models.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.inject.*;
import play.mvc.*;
import play.data.*;
import io.ebean.*;
import play.i18n.MessagesApi;
import play.libs.typedmap.TypedKey;


public class Attrs {
    public static final TypedKey<UserEntity> USER = TypedKey.<UserEntity>create("user");
}
