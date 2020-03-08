package com.pedro.vertx.http;

import com.pedro.vertx.common.BaseVerticle;
import com.pedro.vertx.common.exception.AuthorizationException;
import com.pedro.vertx.common.handlers.FailureHandler;
import com.pedro.vertx.common.RequestValidators;
import com.pedro.vertx.common.ResponseUtil;
import com.pedro.vertx.graphql.GraphQLDataFetchers;
import com.pedro.vertx.graphql.GraphQLProvider;
import com.pedro.vertx.service.ArticleService;
import com.pedro.vertx.service.UserService;
import graphql.GraphQL;
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
import io.vertx.reactivex.ext.web.handler.graphql.GraphQLHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.pedro.vertx.consts.DatabaseConsts.*;
import static com.pedro.vertx.consts.HttpConsts.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class HttpVerticle extends BaseVerticle {

  private final Logger logger = LoggerFactory.getLogger(HttpVerticle.class);

  private com.pedro.vertx.service.reactivex.ArticleService articleService;

  private com.pedro.vertx.service.reactivex.UserService userService;

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
    context.response().putHeader("Content-Type", "text/plain");
    context.response().end("Hello Body!");
  }

  private void loginHandler(RoutingContext context) {
    JsonObject body = context.getBodyAsJson();
    final String username = body.getString("username");
    final String password = body.getString("password");
    userService
      .rxGetUserAndComparePassword(username, password)
      .subscribe(
        ok -> {
          if (!ok) {
            context.fail(400, new AuthorizationException("password is invalid"));
          } else {
            String token = jwt.generateToken(new JsonObject().put("username", username), new JWTOptions().setExpiresInMinutes(60));
            ResponseUtil.generateSuccessResponse(context, 200, new JsonObject().put("token", token));
          }
        },
        context::fail
      );
  }

  private void articlesHandler(RoutingContext context) {
    final JsonObject principal = context.user().principal();
    logger.info("{}", principal);
    articleService
      .rxFetchAllArticles()
      .subscribe(
        articles -> ResponseUtil.generateSuccessResponse(context, new JsonArray(articles)),
        context::fail
      );
  }


  private void searchArticleHandler(RoutingContext context) {
    final String keyword = context.queryParam("q").get(0);
    articleService
      .rxSearchArticle(keyword)
      .subscribe(
        articles -> ResponseUtil.generateSuccessResponse(context, new JsonArray(articles)),
        context::fail
      );
  }

  private void initServices() {
    articleService = new com.pedro.vertx.service.reactivex.ArticleService(ArticleService.createProxy(vertx.getDelegate(), ARTICLE_SERVICE_QUEUE));
    userService = new com.pedro.vertx.service.reactivex.UserService(UserService.createProxy(vertx.getDelegate(), USER_SERVICE_QUEUE));
  }

  private HttpServer initHttpServer() throws IOException {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.route().failureHandler(new FailureHandler());
    router.route().handler(BodyHandler.create());

    String secret = config().getString(TOKEN_SECRET_CONFIG, "secret");

    jwt = JWTAuth.create(vertx, new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions().setAlgorithm("HS256").setPublicKey(secret).setSymmetric(true))
    );

    router.get("/").handler(this::indexHandler);
    router.post("/login").handler(RequestValidators.loginValidator()).handler(this::loginHandler);

    GraphQL graphQL = setupGraphQLJava();
    router.route("/graphql").handler(GraphQLHandler.create(graphQL));
    router.route("/api/*").handler(JWTAuthHandler.create(jwt, "/login"));
    router.get("/api/article").handler(this::articlesHandler);
    router.get("/api/article/search").handler(RequestValidators.keywordValidator()).handler(this::searchArticleHandler);

    server.requestHandler(router);
    return server;
  }

  private GraphQL setupGraphQLJava() throws IOException {
    GraphQLDataFetchers dataFetchers = new GraphQLDataFetchers(articleService);
    GraphQLProvider provider = new GraphQLProvider(dataFetchers);
    return provider.graphQL();
  }
}
