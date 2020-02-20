package com.pedro.vertx.database.gen.tables.mappers;

import io.vertx.sqlclient.Row;
import java.util.function.Function;

public class RowMappers {

    private RowMappers(){}

    public static Function<Row,com.pedro.vertx.database.gen.tables.pojos.Article> getArticleMapper() {
        return row -> {
            com.pedro.vertx.database.gen.tables.pojos.Article pojo = new com.pedro.vertx.database.gen.tables.pojos.Article();
            pojo.setId(row.getInteger("id"));
            pojo.setTitle(row.getString("title"));
            pojo.setContent(row.getString("content"));
            // Omitting unrecognized type DataType [ t=tsvector; p=0; s=0; u="pg_catalog"."tsvector"; j=null ] (java.lang.Object) for column fts!
            return pojo;
        };
    }

    public static Function<Row,com.pedro.vertx.database.gen.tables.pojos.BlogUser> getBlogUserMapper() {
        return row -> {
            com.pedro.vertx.database.gen.tables.pojos.BlogUser pojo = new com.pedro.vertx.database.gen.tables.pojos.BlogUser();
            pojo.setId(row.getInteger("id"));
            pojo.setUsername(row.getString("username"));
            pojo.setPassword(row.getString("password"));
            return pojo;
        };
    }

}
