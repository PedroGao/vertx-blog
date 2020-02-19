package com.pedro.vertx.service.impl;

import com.pedro.vertx.common.RowsUtil;
import com.pedro.vertx.service.ArticleService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.SingleHelper;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

  private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private PgPool client;

  private JsonObject config;

  public ArticleServiceImpl(JsonObject config, PgPool client) {
    this.client = client;
    this.config = config;
  }

  @Override
  public ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler) {
    String sql = config.getString("sql.ArticleService.fetchAllArticles");
    client
      .rxQuery(sql)
      .map(RowsUtil::row2List)
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }

  @Override
  public ArticleService searchArticle(String keyword, Handler<AsyncResult<List<JsonObject>>> handler) {
    String sql = config.getString("sql.ArticleService.searchArticle");
    client
      .rxPreparedQuery(sql, Tuple.of(keyword))
      .map(RowsUtil::row2List)
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }
}
