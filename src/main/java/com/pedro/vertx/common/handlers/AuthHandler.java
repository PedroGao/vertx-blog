package com.pedro.vertx.common.handlers;

import com.pedro.vertx.common.exception.AuthorizationException;
import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

public class AuthHandler implements Handler<RoutingContext> {

  @Override
  public void handle(RoutingContext context) {
    context.fail(400, new AuthorizationException("authorization failed"));
  }

}
