package com.pedro.vertx.service.impl;

import com.lin.cms.core.utils.EncryptUtil;
import com.pedro.vertx.common.exception.AuthorizationException;
import com.pedro.vertx.service.UserService;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.SingleHelper;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private JDBCClient client;

  private JsonObject config;

  public UserServiceImpl(JsonObject config, JDBCClient client) {
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
    return client.rxQueryWithParams(sql, new JsonArray().add(username))
      .map(
        rc -> {
          List<JsonObject> rows = rc.getRows();
          if (rows == null || rows.size() == 0) {
            throw new AuthorizationException("user is not found");
          }
          return rows.get(0);
        }
      );
  }
}
