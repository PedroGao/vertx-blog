package com.pedro.vertx.model;

import cn.hutool.core.bean.BeanUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

public class User extends AbstractUser {

  private Long id;

  private String username;

  private String password;

  public User() {
  }

  public User(JsonObject json) {
    Article article = Json.decodeValue(json.encode(), Article.class);
    BeanUtil.copyProperties(article, this);
  }

  public JsonObject toJson() {
    return JsonObject.mapFrom(this);
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      '}';
  }

  @Override
  public JsonObject principal() {
    return new JsonObject().put("id", id).put("username", username);
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {
    // authProvider.authenticate();
  }


  @Override
  protected void doIsPermitted(String s, Handler<AsyncResult<Boolean>> handler) {
    handler.handle(Future.factory.succeededFuture(true));
  }
}
