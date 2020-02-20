package com.pedro.vertx.service.impl;

import com.pedro.vertx.common.RowsUtil;
import com.pedro.vertx.database.JooqConfiguration;
import com.pedro.vertx.database.gen.tables.daos.ArticleDao;
import com.pedro.vertx.database.gen.tables.interfaces.IArticle;
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
import java.util.stream.Collectors;

public class ArticleServiceImpl implements ArticleService {

  private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private PgPool client;

  private JsonObject config;

  private ArticleDao articleDao;

  public ArticleServiceImpl(JsonObject config, PgPool client) {
    this.client = client;
    this.config = config;
    articleDao = new ArticleDao(JooqConfiguration.configuration, client);
  }

  @Override
  public ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler) {
    articleDao
      .findAll()
      .map(articles -> articles.stream().map(IArticle::toJson).collect(Collectors.toList()))
      .subscribe(SingleHelper.toObserver(handler));
    // String sql = config.getString("sql.ArticleService.fetchAllArticles");
    // client
    //   .rxQuery(sql)
    //   .map(RowsUtil::row2List)
    //   .subscribe(SingleHelper.toObserver(handler));
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
