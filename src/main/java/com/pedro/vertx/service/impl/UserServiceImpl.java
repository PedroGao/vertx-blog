package com.pedro.vertx.service.impl;

import com.pedro.vertx.common.exception.AuthorizationException;
import com.pedro.vertx.database.JooqConfiguration;
import com.pedro.vertx.database.gen.tables.BlogUser;
import com.pedro.vertx.database.gen.tables.daos.BlogUserDao;
import com.pedro.vertx.service.UserService;
import io.github.talelin.core.utils.EncryptUtil;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.SingleHelper;
import io.vertx.reactivex.pgclient.PgPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private PgPool client;

  private JsonObject config;

  private BlogUserDao userDao;

  public UserServiceImpl(JsonObject config, PgPool client) {
    this.client = client;
    this.config = config;
    userDao = new BlogUserDao(JooqConfiguration.configuration, client);
  }

  @Override
  public UserService getUserByUsername(String username, Handler<AsyncResult<JsonObject>> handler) {
    queryUserByUsername(username).subscribe(SingleHelper.toObserver(handler));
    return this;
  }

  @Override
  public UserService getUserAndComparePassword(String username, String password, Handler<AsyncResult<Boolean>> handler) {
    queryUserByUsername(username)
      .map(
        data -> {
          String encryptedPassword = data.getString("password");
          return EncryptUtil.verify(encryptedPassword, password);
        }
      )
      .subscribe(SingleHelper.toObserver(handler));
    return this;
  }

  private Single<JsonObject> queryUserByUsername(String username) {
    return userDao
      .findOneByCondition(BlogUser.BLOG_USER.USERNAME.eq(username))
      .map(user -> {
        if (user.isPresent()) {
          return user.get().toJson();
        } else {
          throw new AuthorizationException("user is not found");
        }
      });
  }
}
