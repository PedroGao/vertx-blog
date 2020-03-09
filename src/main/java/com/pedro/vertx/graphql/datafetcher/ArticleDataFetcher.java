package com.pedro.vertx.graphql.datafetcher;

import com.pedro.vertx.service.reactivex.ArticleService;
import graphql.schema.DataFetcher;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ArticleDataFetcher {
  private ArticleService articleService;

  public ArticleDataFetcher(ArticleService articleService) {
    this.articleService = articleService;
  }

  public DataFetcher getArticleByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<Map<String, Object>> completableFuture = new CompletableFuture<>();
      Integer id = dataFetchingEnvironment.getArgument("id");
      articleService
        .rxGetArticleById(id)
        .map(JsonObject::getMap)
        .subscribe(completableFuture::complete, completableFuture::completeExceptionally);
      return completableFuture;
    };
  }

  public DataFetcher searchArticlesDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<List<Map<String, Object>>> completableFuture = new CompletableFuture<>();
      String keyword = dataFetchingEnvironment.getArgument("keyword");
      articleService
        .rxSearchArticle(keyword)
        .map(jsonObjects -> jsonObjects.stream().map(JsonObject::getMap).collect(Collectors.toList()))
        .subscribe(completableFuture::complete, completableFuture::completeExceptionally);
      return completableFuture;
    };
  }
}
