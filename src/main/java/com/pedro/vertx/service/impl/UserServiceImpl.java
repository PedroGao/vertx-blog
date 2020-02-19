package com.pedro.vertx.service.impl;

import com.pedro.vertx.common.RowsUtil;
import com.pedro.vertx.common.exception.AuthorizationException;
import com.pedro.vertx.service.UserService;
import io.github.talelin.core.utils.EncryptUtil;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.SingleHelper;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private PgPool client;

  private JsonObject config;

  public UserServiceImpl(JsonObject config, PgPool client) {
    this.client = client;
    this.config = config;
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
    String sql = config.getString("sql.UserService.getUserByUsername");
    return client.rxPreparedQuery(sql, Tuple.of(username))
      .map(RowsUtil::row2List)
      .map(
        arr -> {
          if (arr == null || arr.size() == 0) {
            throw new AuthorizationException("user is not found");
          }
          return arr.get(0);
        }
      );
  }
}
