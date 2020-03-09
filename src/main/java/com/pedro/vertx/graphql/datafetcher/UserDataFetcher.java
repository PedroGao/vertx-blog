package com.pedro.vertx.graphql.datafetcher;

import com.pedro.vertx.common.exception.AuthorizationException;
import com.pedro.vertx.service.reactivex.UserService;
import graphql.schema.DataFetcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.reactivex.ext.auth.jwt.JWTAuth;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class UserDataFetcher {

  private UserService userService;
  private JWTAuth jwt;

  public UserDataFetcher(UserService userService, JWTAuth jwt) {
    this.userService = userService;
    this.jwt = jwt;
  }

  public DataFetcher getUserByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<Map<String, Object>> completableFuture = new CompletableFuture<>();
      final Integer id = dataFetchingEnvironment.getArgument("id");
      userService
        .rxGetUserById(id)
        .map(JsonObject::getMap)
        .subscribe(completableFuture::complete, completableFuture::completeExceptionally);
      return completableFuture;
    };
  }

  public DataFetcher loginDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<Map<String, Object>> completableFuture = new CompletableFuture<>();
      final String username = dataFetchingEnvironment.getArgument("username");
      final String password = dataFetchingEnvironment.getArgument("password");
      userService
        .rxGetUserAndComparePassword(username, password)
        .subscribe(
          ok -> {
            if (!ok) {
              completableFuture.completeExceptionally(new AuthorizationException("password is invalid"));
            } else {
              String token = jwt.generateToken(new JsonObject().put("username", username), new JWTOptions().setExpiresInMinutes(60));
              completableFuture.complete(new JsonObject().put("token", token).getMap());
            }
          },
          completableFuture::completeExceptionally
        );
      return completableFuture;
    };
  }
}
