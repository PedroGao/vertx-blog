package com.pedro.vertx.model;

import cn.hutool.core.bean.BeanUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class Article {

  private Long id;

  private String title;

  private String content;

  public Article() {
  }

  public Article(JsonObject json) {
    Article article = Json.decodeValue(json.encode(), Article.class);
    BeanUtil.copyProperties(article, this);
  }

  public JsonObject toJson() {
    return JsonObject.mapFrom(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Article article = (Article) o;
    return id.equals(article.id) &&
      title.equals(article.title) &&
      content.equals(article.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content);
  }

  @Override
  public String toString() {
    return "Article{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      '}';
  }
}
