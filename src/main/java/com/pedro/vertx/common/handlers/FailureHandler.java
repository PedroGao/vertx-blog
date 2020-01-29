package com.pedro.vertx.common.handlers;

import com.pedro.vertx.common.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailureHandler implements Handler<RoutingContext> {

  private final Logger logger = LoggerFactory.getLogger(FailureHandler.class);

  @Override
  public void handle(RoutingContext context) {
    Throwable failure = context.failure();
    logger.error("", failure);
    if (failure instanceof ValidationException) {
      ResponseUtil.generateFailureResponse(context, 400);
    } else if (failure instanceof DecodeException) {
      ResponseUtil.generateFailureResponse(context, 400);
    } else {
      ResponseUtil.generateFailureResponse(context, 500);
    }
  }
}
