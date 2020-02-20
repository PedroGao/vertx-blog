/*
 * This file is generated by jOOQ.
 */
package com.pedro.vertx.database.gen;


import com.pedro.vertx.database.gen.tables.Article;
import com.pedro.vertx.database.gen.tables.BlogUser;
import com.pedro.vertx.database.gen.tables.records.ArticleRecord;
import com.pedro.vertx.database.gen.tables.records.BlogUserRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ArticleRecord, Integer> IDENTITY_ARTICLE = Identities0.IDENTITY_ARTICLE;
    public static final Identity<BlogUserRecord, Integer> IDENTITY_BLOG_USER = Identities0.IDENTITY_BLOG_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ArticleRecord> ARTICLE_PKEY = UniqueKeys0.ARTICLE_PKEY;
    public static final UniqueKey<BlogUserRecord> BLOG_USER_PKEY = UniqueKeys0.BLOG_USER_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ArticleRecord, Integer> IDENTITY_ARTICLE = Internal.createIdentity(Article.ARTICLE, Article.ARTICLE.ID);
        public static Identity<BlogUserRecord, Integer> IDENTITY_BLOG_USER = Internal.createIdentity(BlogUser.BLOG_USER, BlogUser.BLOG_USER.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<ArticleRecord> ARTICLE_PKEY = Internal.createUniqueKey(Article.ARTICLE, "article_pkey", Article.ARTICLE.ID);
        public static final UniqueKey<BlogUserRecord> BLOG_USER_PKEY = Internal.createUniqueKey(BlogUser.BLOG_USER, "blog_user_pkey", BlogUser.BLOG_USER.ID);
    }
}