/*
 * This file is generated by jOOQ.
 */
package com.pedro.vertx.database.gen.tables;


import com.pedro.vertx.database.gen.Indexes;
import com.pedro.vertx.database.gen.Keys;
import com.pedro.vertx.database.gen.Public;
import com.pedro.vertx.database.gen.tables.records.ArticleRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Article extends TableImpl<ArticleRecord> {

    private static final long serialVersionUID = 694876172;

    /**
     * The reference instance of <code>public.article</code>
     */
    public static final Article ARTICLE = new Article();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ArticleRecord> getRecordType() {
        return ArticleRecord.class;
    }

    /**
     * The column <code>public.article.id</code>.
     */
    public final TableField<ArticleRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('article_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.article.title</code>.
     */
    public final TableField<ArticleRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(40), this, "");

    /**
     * The column <code>public.article.content</code>.
     */
    public final TableField<ArticleRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.article</code> table reference
     */
    public Article() {
        this(DSL.name("article"), null);
    }

    /**
     * Create an aliased <code>public.article</code> table reference
     */
    public Article(String alias) {
        this(DSL.name(alias), ARTICLE);
    }

    /**
     * Create an aliased <code>public.article</code> table reference
     */
    public Article(Name alias) {
        this(alias, ARTICLE);
    }

    private Article(Name alias, Table<ArticleRecord> aliased) {
        this(alias, aliased, null);
    }

    private Article(Name alias, Table<ArticleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Article(Table<O> child, ForeignKey<O, ArticleRecord> key) {
        super(child, key, ARTICLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ARTICLE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ArticleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ARTICLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ArticleRecord> getPrimaryKey() {
        return Keys.ARTICLE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ArticleRecord>> getKeys() {
        return Arrays.<UniqueKey<ArticleRecord>>asList(Keys.ARTICLE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article as(String alias) {
        return new Article(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article as(Name alias) {
        return new Article(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Article rename(String name) {
        return new Article(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Article rename(Name name) {
        return new Article(name, null);
    }
}
