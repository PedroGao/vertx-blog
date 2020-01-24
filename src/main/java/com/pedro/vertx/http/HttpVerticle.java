package com.pedro.vertx.http;

import com.pedro.vertx.common.BaseVerticle;
import com.pedro.vertx.common.handlers.FailureHandler;
import com.pedro.vertx.common.RequestValidators;
import com.pedro.vertx.common.ResponseUtil;
import com.pedro.vertx.model.Article;
import com.pedro.vertx.service.ArticleService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.auth.jwt.JWTAuth;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.JWTAuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.stream.Collectors;

import static com.pedro.vertx.consts.DatabaseConsts.ARTICLE_SERVICE_QUEUE;
import static com.pedro.vertx.consts.HttpConsts.DEFAULT_HTTP_PORT;
import static com.pedro.vertx.consts.HttpConsts.HTTP_PORT_CONFIG;

public class HttpVerticle extends BaseVerticle {

  private final Logger logger = LoggerFactory.getLogger(HttpVerticle.class);

  private com.pedro.vertx.service.reactivex.ArticleService articleService;

  private JWTAuth jwt;

  @Override
  public void start() throws Exception {
    initServices();
    HttpServer server = initHttpServer();
    Integer port = config().getInteger(HTTP_PORT_CONFIG, DEFAULT_HTTP_PORT);
    server.rxListen(port)
      .subscribe(
        rx -> logger.info("listening at: {}", port),
        fail -> logger.error("create server error: ", fail.getCause())
      );
  }

  private void indexHandler(RoutingContext context) {
    context.response().end("Hello Body!");
  }

  private void loginHandler(RoutingContext context) {
    String token = jwt.generateToken(new JsonObject().put("user", "pedro"),
      new JWTOptions().setExpiresInMinutes(60));
    ResponseUtil.generateSuccessResponse(context,
      200,
      new JsonObject().put("token", token));
  }

  private void articlesHandler(RoutingContext context) {
    articleService
      .rxFetchAllArticles()
      .subscribe(
        articles -> {
          List<Article> articleList = articles.stream().map(Article::new).collect(Collectors.toList());
          logger.info("articles: {}", articleList);
          ResponseUtil.generateSuccessResponse(context, new JsonArray(articles));
        },
        throwable -> {
          context.fail(throwable);
        }
      );
  }

  private void initServices() {
    articleService = new com.pedro.vertx.service.reactivex.ArticleService(ArticleService.createProxy(vertx.getDelegate(), ARTICLE_SERVICE_QUEUE));
  }

  private HttpServer initHttpServer() {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.route().failureHandler(new FailureHandler());
    router.route().handler(BodyHandler.create());

    jwt = JWTAuth.create(vertx, new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS256")
        .setPublicKey("keyboard cat")
        .setSymmetric(true)));


    router.route("/api/*").handler(JWTAuthHandler.create(jwt, "/login"));

    router.get("/").handler(this::indexHandler);
    router.post("/login").handler(RequestValidators.loginValidator()).handler(this::loginHandler);
    router.get("/api/article").handler(this::articlesHandler);

    server.requestHandler(router);
    return server;
  }
}
