package com.pedro.vertx.service;

import com.pedro.vertx.service.impl.UserServiceImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.pgclient.PgPool;


@ProxyGen
@VertxGen
public interface UserService {

  @Fluent
  UserService getUserByUsername(String username, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  UserService getUserAndComparePassword(String username, String password, Handler<AsyncResult<Boolean>> handler);

  @GenIgnore
  static UserService create(JsonObject config, PgPool client) {
    return new UserServiceImpl(config, client);
  }

  @GenIgnore
  static UserService createProxy(Vertx vertx, String address) {
    return new UserServiceVertxEBProxy(vertx, address);
  }
}
