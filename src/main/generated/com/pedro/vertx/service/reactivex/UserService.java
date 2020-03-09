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
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rx.RxGen(com.pedro.vertx.service.UserService.class)
public class UserService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserService that = (UserService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.rx.TypeArg<UserService> __TYPE_ARG = new io.vertx.lang.rx.TypeArg<>(    obj -> new UserService((com.pedro.vertx.service.UserService) obj),
    UserService::getDelegate
  );

  private final com.pedro.vertx.service.UserService delegate;
  
  public UserService(com.pedro.vertx.service.UserService delegate) {
    this.delegate = delegate;
  }

  public com.pedro.vertx.service.UserService getDelegate() {
    return delegate;
  }

  public com.pedro.vertx.service.reactivex.UserService getUserByUsername(String username, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.getUserByUsername(username, handler);
    return this;
  }

  public Single<JsonObject> rxGetUserByUsername(String username) { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      getUserByUsername(username, handler);
    });
  }

  public com.pedro.vertx.service.reactivex.UserService getUserById(Integer id, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.getUserById(id, handler);
    return this;
  }

  public Single<JsonObject> rxGetUserById(Integer id) { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      getUserById(id, handler);
    });
  }

  public com.pedro.vertx.service.reactivex.UserService getUserAndComparePassword(String username, String password, Handler<AsyncResult<Boolean>> handler) { 
    delegate.getUserAndComparePassword(username, password, handler);
    return this;
  }

  public Single<Boolean> rxGetUserAndComparePassword(String username, String password) { 
    return io.vertx.reactivex.impl.AsyncResultSingle.toSingle(handler -> {
      getUserAndComparePassword(username, password, handler);
    });
  }


  public static  UserService newInstance(com.pedro.vertx.service.UserService arg) {
    return arg != null ? new UserService(arg) : null;
  }
}
