package com.pedro.vertx;

import com.pedro.vertx.http.HttpVerticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class TestMainVerticle {

  private Vertx vertx;

  private WebClient webClient;

  @Before
  public void deploy_verticle(TestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new HttpVerticle(), testContext.asyncAssertSuccess(it -> {
      webClient = WebClient.create(vertx);
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
      .send(ar -> {
        if (ar.succeeded()) {
          HttpResponse<Buffer> response = ar.result();
          context.assertTrue(response.headers().contains("Content-Type"));
          context.assertEquals("text/plain", response.getHeader("Content-Type"));
          context.assertEquals("Hello Body!", response.body().toString());
          async.complete();
        } else {
          context.fail(ar.cause());
        }
      });
    context.async().complete();
  }
}
