package com.pedro.vertx.database;

import com.pedro.vertx.common.BaseVerticle;
import com.pedro.vertx.service.ArticleService;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pedro.vertx.consts.DatabaseConsts.*;

public class DatabaseVerticle extends BaseVerticle {

  private Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

  private JDBCClient client;

  @Override
  public void start() throws Exception {
    initClient();
    bindServices();
  }

  @Override
  public void stop() throws Exception {
    client.close();
  }

  private void initClient() {
    String url = config().getString(DB_URL_CONFIG, DEFAULT_DB_URL);
    String driverClass = config().getString(DB_DRIVER_CLASS_CONFIG, DEFAULT_DRIVER_CLASS);
    String user = config().getString(DB_USER_CONFIG, DEFAULT_DB_USER);
    String password = config().getString(DB_PASSWORD_CONFIG, DEFAULT_DB_PASSWORD);

    JsonObject clientConfig = new JsonObject();
    clientConfig.put("url", url);
    clientConfig.put("driver_class", driverClass);
    clientConfig.put("user", user);
    clientConfig.put("password", password);
    client = JDBCClient.createShared(vertx, clientConfig);
    client.getConnection(rc -> {
      if (rc.failed()) {
        logger.error("database connect error:", rc.cause());
      }
    });
  }

  private void bindServices() {
    ArticleService articleService = ArticleService.create(client);
    ServiceBinder binder = new ServiceBinder(vertx.getDelegate());
    binder.setAddress(ARTICLE_SERVICE_QUEUE).register(ArticleService.class, articleService);
  }
}
