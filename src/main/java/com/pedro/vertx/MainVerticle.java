package com.pedro.vertx;

import com.pedro.vertx.common.BaseVerticle;
import io.vertx.core.DeploymentOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainVerticle extends BaseVerticle {

  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() throws Exception {
    logger.info("config: {} ", config().encodePrettily());
    DeploymentOptions httpOptions = new DeploymentOptions().setInstances(2);
    vertx
      .rxDeployVerticle("com.pedro.vertx.database.DatabaseVerticle")
      .flatMap(it -> vertx.rxDeployVerticle("com.pedro.vertx.http.HttpVerticle", httpOptions))
      .subscribe(
        ok -> logger.info("vertx deploy ok : {}", ok),
        err -> logger.error("vertx deploy err:", err)
      );
  }
}
