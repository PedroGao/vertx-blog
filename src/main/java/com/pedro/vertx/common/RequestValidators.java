package com.pedro.vertx.common;

import cn.hutool.core.io.resource.ResourceUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.validation.ParameterType;
import io.vertx.reactivex.ext.web.api.validation.HTTPRequestValidationHandler;

public class RequestValidators {

  public static final JsonObject schemas;

  static {
    schemas = new JsonObject(Buffer.buffer(ResourceUtil.readBytes("json-schemas.json")));
  }

  public static HTTPRequestValidationHandler loginValidator() {
    return HTTPRequestValidationHandler
      .create()
      .addExpectedContentType("application/json")
      .addJsonBodySchema(schemas.getJsonObject("login").encode());
  }

  public static HTTPRequestValidationHandler keywordValidator() {
    return HTTPRequestValidationHandler
      .create()
      .addQueryParam("q", ParameterType.GENERIC_STRING, true);
  }
}
