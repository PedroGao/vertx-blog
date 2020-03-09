package com.pedro.vertx.graphql;

import com.google.common.collect.ImmutableMap;
import com.pedro.vertx.service.reactivex.ArticleService;
import graphql.schema.DataFetcher;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class GraphQLDataFetchers {
  private static List<Map<String, String>> books = Arrays.asList(
    ImmutableMap.of("id", "book-1",
      "name", "Harry Potter and the Philosopher's Stone",
      "pageCount", "223",
      "authorId", "author-1"),
    ImmutableMap.of("id", "book-2",
      "name", "Moby Dick",
      "pageCount", "635",
      "authorId", "author-2"),
    ImmutableMap.of("id", "book-3",
      "name", "Interview with the vampire",
      "pageCount", "371",
      "authorId", "author-3")
  );

  private static List<Map<String, String>> authors = Arrays.asList(
    ImmutableMap.of("id", "author-1",
      "firstName", "Joanne",
      "lastName", "Rowling"),
    ImmutableMap.of("id", "author-2",
      "firstName", "Herman",
      "lastName", "Melville"),
    ImmutableMap.of("id", "author-3",
      "firstName", "Anne",
      "lastName", "Rice")
  );

  private ArticleService articleService;

  public GraphQLDataFetchers(ArticleService articleService) {
    this.articleService = articleService;
  }

  public DataFetcher<CompletionStage<Map<String, String>>> getBookByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String bookId = dataFetchingEnvironment.getArgument("id");
      CompletableFuture<Map<String, String>> completableFuture = new CompletableFuture<>();
      // completableFuture.completeExceptionally(ar.cause());
      Map<String, String> hit = books
        .stream()
        .filter(book -> book.get("id").equals(bookId))
        .findFirst()
        .orElse(null);
      completableFuture.complete(hit);
      return completableFuture;
    };
  }

  public DataFetcher<CompletionStage<Map<String, String>>> getAuthorDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<Map<String, String>> completableFuture = new CompletableFuture<>();
      Map<String, String> book = dataFetchingEnvironment.getSource();
      String authorId = book.get("authorId");
      Map<String, String> hit = authors
        .stream()
        .filter(author -> author.get("id").equals(authorId))
        .findFirst()
        .orElse(null);
      completableFuture.complete(hit);
      return completableFuture;
    };
  }

  public DataFetcher<CompletionStage<Integer>> getPageCountDataFetcher() {
    return dataFetchingEnvironment -> {
      CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
      Map<String, String> source = dataFetchingEnvironment.getSource();
      int hit = source.size();
      completableFuture.complete(hit);
      return completableFuture;
    };
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
}
