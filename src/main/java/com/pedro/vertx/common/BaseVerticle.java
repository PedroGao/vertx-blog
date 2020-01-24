package com.pedro.vertx.common;

import cn.hutool.core.io.resource.ResourceUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;

public class BaseVerticle extends AbstractVerticle {

  public JsonObject config() {
    JsonObject json = super.config();
    if (json.isEmpty()) {
      byte[] bytes = ResourceUtil.readBytes("application.json");
      return new JsonObject(Buffer.buffer(bytes));
    }
    return json;
  }
}
