package com.pedro.vertx;

import com.pedro.vertx.database.DatabaseVerticle;
import com.pedro.vertx.service.ArticleService;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pedro.vertx.consts.DatabaseConsts.ARTICLE_SERVICE_QUEUE;

@RunWith(VertxUnitRunner.class)
public class TestDatabaseVerticle {

  private final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

  private Vertx vertx;

  private ArticleService articleService;

  @Before
  public void prepare(TestContext context) throws InterruptedException {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new DatabaseVerticle(), context.asyncAssertSuccess(id -> {
      articleService = ArticleService.createProxy(vertx, ARTICLE_SERVICE_QUEUE);
    }));
  }

  @Test
  public void fetchAllArticles(TestContext context) {
    Async async = context.async();
    articleService.fetchAllArticles(
      context.asyncAssertSuccess(rc -> {
          logger.info("rc: {}", rc);
          boolean anyMatch = rc.stream().anyMatch(it -> it.getLong("id").equals(1L));
          context.assertTrue(anyMatch);
          async.complete();
        }
      ));
    async.await();
  }

  @After
  public void finish(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

}
