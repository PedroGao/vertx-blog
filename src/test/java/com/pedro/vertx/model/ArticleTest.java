package com.pedro.vertx.model;

import com.pedro.vertx.database.gen.tables.pojos.Article;
import io.vertx.core.json.JsonObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleTest {

  private final Logger logger = LoggerFactory.getLogger(ArticleTest.class);

  @Test
  public void toJson() {
    Article article = new Article();
    article.setId(1);
    article.setTitle("pedro");
    article.setContent("lll");
    JsonObject json = article.toJson();
    logger.info("{}", json);

    Article copy = new Article(json);
    logger.info("{}", copy);

    logger.info("{}", copy.equals(article));
  }
}
