package com.pedro.vertx.common;

import cn.hutool.core.io.resource.ResourceUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;

public class BaseVerticle extends AbstractVerticle {

  public JsonObject config() {
    JsonObject config = super.config();
    JsonObject applicationConfig = readApplicationConfig();
    JsonObject sqlConfig = readSQLConfig();
    config.mergeIn(applicationConfig, true);
    config.mergeIn(sqlConfig, true);
    return config;
  }

  private JsonObject readApplicationConfig() {
    byte[] bytes = ResourceUtil.readBytes("application.json");
    return new JsonObject(Buffer.buffer(bytes));
  }

  private JsonObject readSQLConfig() {
    byte[] bytes = ResourceUtil.readBytes("sql.json");
    return new JsonObject(Buffer.buffer(bytes));
  }
}
