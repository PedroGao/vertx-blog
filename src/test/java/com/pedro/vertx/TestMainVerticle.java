package com.pedro.vertx;

import com.pedro.vertx.database.DatabaseVerticle;
import com.pedro.vertx.http.HttpVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(VertxUnitRunner.class)
public class TestMainVerticle {

  private Vertx vertx;

  private WebClient webClient;

  private final Logger logger = LoggerFactory.getLogger(TestMainVerticle.class);

  @Before
  public void deploy_verticle(TestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new DatabaseVerticle(), testContext.asyncAssertSuccess(it -> {
      vertx.deployVerticle(new HttpVerticle(), testContext.asyncAssertSuccess(it1 -> {
          webClient = WebClient.create(vertx);
        }
      ));
    }));
  }

  @After
  public void verticle_deployed(TestContext context) throws Throwable {
    webClient.close();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void indexHandler(TestContext context) throws Throwable {
    Async async = context.async();
    webClient
      .get(5000, "localhost", "/")
      .send(context.asyncAssertSuccess(response -> {
        context.assertTrue(response.headers().contains("Content-Type"));
        context.assertEquals("text/plain", response.getHeader("Content-Type"));
        context.assertEquals("Hello Body!", response.body().toString());
        async.complete();
      }));
    context.async().complete();
  }


  @Test
  public void loginHandler(TestContext context) throws Throwable {
    Async async = context.async();
    webClient
      .post(5000, "localhost", "/login")
      .sendJsonObject(new JsonObject().put("username", "pedro").put("password", "123456"),
        context.asyncAssertSuccess(response -> {
          logger.info("body: {}", response.bodyAsJsonObject());
          context.assertTrue(response.headers().contains("Content-Type"));
          context.assertNotNull(new JsonObject(response.body().getDelegate()).getString("token"));
          async.complete();
        }));
    context.async().complete();
  }
}
