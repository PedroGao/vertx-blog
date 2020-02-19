package com.pedro.vertx;

import com.pedro.vertx.common.BaseVerticle;
import com.pedro.vertx.database.DatabaseVerticle;
import com.pedro.vertx.http.HttpVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends BaseVerticle {

  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() throws Exception {
    logger.info("config: {} ", config().encodePrettily());
    vertx
      .rxDeployVerticle(new DatabaseVerticle())
      .flatMap(it -> vertx.rxDeployVerticle(new HttpVerticle()))
      .subscribe(
        ok -> logger.info("vertx deploy ok : {}", ok),
        err -> logger.error("vertx deploy err: {}", err.getMessage())
      );
  }
}
