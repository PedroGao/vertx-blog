package com.pedro.vertx.database;

import com.pedro.vertx.common.BaseVerticle;
import com.pedro.vertx.service.ArticleService;
import com.pedro.vertx.service.UserService;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.sqlclient.PoolOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pedro.vertx.consts.DatabaseConsts.*;

public class DatabaseVerticle extends BaseVerticle {

  private final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

  private PgPool client;

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
    String host = config().getString(DB_HOST_CONFIG, DEFAULT_DB_HOST);
    Integer port = config().getInteger(DB_PORT_CONFIG, DEFAULT_DB_PORT);
    String user = config().getString(DB_USER_CONFIG, DEFAULT_DB_USER);
    String password = config().getString(DB_PASSWORD_CONFIG, DEFAULT_DB_PASSWORD);
    String db = config().getString(DB_VALUE_CONFIG, DEFAULT_DB_VALUE);

    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(port)
      .setHost(host)
      .setDatabase(db)
      .setUser(user)
      .setPassword(password);
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    client = PgPool.pool(vertx, connectOptions, poolOptions);
    client.getConnection(rc -> {
      if (rc.failed()) {
        logger.error("database connect error:", rc.cause());
      }
    });
  }

  private void bindServices() {
    ServiceBinder binder = new ServiceBinder(vertx.getDelegate());

    ArticleService articleService = ArticleService.create(config(), client);
    binder.setAddress(ARTICLE_SERVICE_QUEUE).register(ArticleService.class, articleService);

    UserService userService = UserService.create(config(), client);
    binder.setAddress(USER_SERVICE_QUEUE).register(UserService.class, userService);
  }
}
