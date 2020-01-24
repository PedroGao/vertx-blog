package com.pedro.vertx.service.impl;

import com.pedro.vertx.service.ArticleService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
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

  public ArticleServiceImpl(JDBCClient client) {
    this.client = client;
  }

  @Override
  public ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler) {
    client
      .rxQuery("select id,title,content from article")
      .map(ResultSet::getRows)
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }
}
