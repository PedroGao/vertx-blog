package com.pedro.vertx.model;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

  private final Logger logger = LoggerFactory.getLogger(ArticleTest.class);

  @Test
  void toJson() {
    Article article = new Article();
    article.setId(1L);
    article.setTitle("pedro");
    article.setContent("lll");
    JsonObject json = article.toJson();
    logger.info("{}", json);

    Article copy = new Article(json);
    logger.info("{}", copy);

    logger.info("{}", copy.equals(article));
  }
}
