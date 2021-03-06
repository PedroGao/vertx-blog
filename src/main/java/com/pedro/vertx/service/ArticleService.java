package com.pedro.vertx.service;

import com.pedro.vertx.service.impl.ArticleServiceImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.pgclient.PgPool;

import java.util.List;


@ProxyGen
@VertxGen
public interface ArticleService {

  @Fluent
  ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler);

  @Fluent
  ArticleService searchArticle(String keyword, Handler<AsyncResult<List<JsonObject>>> handler);

  @Fluent
  ArticleService getArticleById(Integer id, Handler<AsyncResult<JsonObject>> handler);

  @GenIgnore
  static ArticleService create(JsonObject config, PgPool client) {
    return new ArticleServiceImpl(config, client);
  }

  @GenIgnore
  static ArticleService createProxy(Vertx vertx, String address) {
    return new ArticleServiceVertxEBProxy(vertx, address);
  }
}
