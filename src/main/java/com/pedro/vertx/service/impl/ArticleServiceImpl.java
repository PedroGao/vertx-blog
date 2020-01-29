package com.pedro.vertx.service.impl;

import com.pedro.vertx.service.ArticleService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.reactivex.SingleHelper;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

  private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private JDBCClient client;

  private JsonObject config;

  public ArticleServiceImpl(JsonObject config, JDBCClient client) {
    this.client = client;
    this.config = config;
  }

  @Override
  public ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler) {
    String sql = config.getString("sql.ArticleService.fetchAllArticles");
    client
      .rxQuery(sql)
      .map(ResultSet::getRows)
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }

  @Override
  public ArticleService searchArticle(String keyword, Handler<AsyncResult<List<JsonObject>>> handler) {
    String sql = config.getString("sql.ArticleService.searchArticle");
    client
      .rxQueryWithParams(sql, new JsonArray().add(keyword))
      .map(ResultSet::getRows)
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }
}
