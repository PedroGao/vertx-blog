/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.pedro.vertx.service.reactivex;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rx.RxGen(com.pedro.vertx.service.ArticleService.class)
public class ArticleService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleService that = (ArticleService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.rx.TypeArg<ArticleService> __TYPE_ARG = new io.vertx.lang.rx.TypeArg<>(    obj -> new ArticleService((com.pedro.vertx.service.ArticleService) obj),
    ArticleService::getDelegate
  );

  private final com.pedro.vertx.service.ArticleService delegate;
  
  public ArticleService(com.pedro.vertx.service.ArticleService delegate) {
    this.delegate = delegate;
  }

  public com.pedro.vertx.service.ArticleService getDelegate() {
    return delegate;
  }

  public com.pedro.vertx.service.reactivex.ArticleService fetchAllArticles(Handler<AsyncResult<List<JsonObject>>> handler) { 
    delegate.fetchAllArticles(handler);
    return this;
  }

  public Single<List<JsonObject>> rxFetchAllArticles() { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      fetchAllArticles(handler);
    });
  }

  public com.pedro.vertx.service.reactivex.ArticleService searchArticle(String keyword, Handler<AsyncResult<List<JsonObject>>> handler) { 
    delegate.searchArticle(keyword, handler);
    return this;
  }

  public Single<List<JsonObject>> rxSearchArticle(String keyword) { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      searchArticle(keyword, handler);
    });
  }

  public com.pedro.vertx.service.reactivex.ArticleService getArticleById(Integer id, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.getArticleById(id, handler);
    return this;
  }

  public Single<JsonObject> rxGetArticleById(Integer id) { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      getArticleById(id, handler);
    });
  }


  public static  ArticleService newInstance(com.pedro.vertx.service.ArticleService arg) {
    return arg != null ? new ArticleService(arg) : null;
  }
}
