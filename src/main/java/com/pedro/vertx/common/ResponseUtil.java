package com.pedro.vertx.common;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class ResponseUtil {

  public static void generateSuccessResponse(RoutingContext context, int statusCode, String body) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    context.response().setStatusCode(statusCode).end(body);
  }

  public static void generateFailureResponse(RoutingContext context, int statusCode) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    JsonObject body = new JsonObject()
      .put("request", context.request().method() + " " + context.request().uri())
      .put("message", context.failure().getMessage())
      .put("timestamp", System.currentTimeMillis());
    context.response().setStatusCode(statusCode).end(body.encode());
  }

  public static void generateFailureResponse(RoutingContext context, int statusCode, Object message) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    JsonObject body = new JsonObject()
      .put("request", context.request().method() + " " + context.request().uri())
      .put("message", message)
      .put("timestamp", System.currentTimeMillis());
    context.response().setStatusCode(statusCode).end(body.encode());
  }

  public static void generateFailureResponse(RoutingContext context, int statusCode, JsonObject body) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    context.response().setStatusCode(statusCode).end(body.encode());
  }


  public static void generateSuccessResponse(RoutingContext context, int statusCode, JsonObject body) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    context.response().setStatusCode(statusCode).end(body.encode());
  }

  public static void generateSuccessResponse(RoutingContext context, int statusCode, JsonArray body) {
    context.response().putHeader("Content-Type", "application/json; charset=utf-8");
    context.response().setStatusCode(statusCode).end(body.encode());
  }

  public static void generateSuccessResponse(RoutingContext context, int statusCode) {
    generateSuccessResponse(context, statusCode, "");
  }

  public static void generateSuccessResponse(RoutingContext context, JsonObject body) {
    generateSuccessResponse(context, 200, body);
  }

  public static void generateSuccessResponse(RoutingContext context, JsonArray body) {
    generateSuccessResponse(context, 200, body);
  }
}
