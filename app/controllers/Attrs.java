package controllers;

import models.UserEntity;
import play.libs.typedmap.TypedKey;

public class Attrs {
    public static final TypedKey<UserEntity> USER = TypedKey.<UserEntity>create("user");
}
