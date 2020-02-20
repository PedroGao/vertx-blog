/*
 * This file is generated by jOOQ.
 */
package com.pedro.vertx.database.gen.tables.interfaces;


import io.github.jklingsporn.vertx.jooq.shared.UnexpectedJsonValueType;
import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;

import java.io.Serializable;

import javax.annotation.Generated;


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
public interface IArticle extends VertxPojo, Serializable {

    /**
     * Setter for <code>public.article.id</code>.
     */
    public IArticle setId(Integer value);

    /**
     * Getter for <code>public.article.id</code>.
     */
    public Integer getId();

    /**
     * Setter for <code>public.article.title</code>.
     */
    public IArticle setTitle(String value);

    /**
     * Getter for <code>public.article.title</code>.
     */
    public String getTitle();

    /**
     * Setter for <code>public.article.content</code>.
     */
    public IArticle setContent(String value);

    /**
     * Getter for <code>public.article.content</code>.
     */
    public String getContent();

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @java.lang.Deprecated
    public IArticle setFts(Object value);

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @java.lang.Deprecated
    public Object getFts();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface IArticle
     */
    public void from(com.pedro.vertx.database.gen.tables.interfaces.IArticle from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface IArticle
     */
    public <E extends com.pedro.vertx.database.gen.tables.interfaces.IArticle> E into(E into);

    @Override
    public default IArticle fromJson(io.vertx.core.json.JsonObject json) {
        try {
            setId(json.getInteger("id"));
        } catch (java.lang.ClassCastException e) {
            throw new UnexpectedJsonValueType("id","java.lang.Integer",e);
        }
        try {
            setTitle(json.getString("title"));
        } catch (java.lang.ClassCastException e) {
            throw new UnexpectedJsonValueType("title","java.lang.String",e);
        }
        try {
            setContent(json.getString("content"));
        } catch (java.lang.ClassCastException e) {
            throw new UnexpectedJsonValueType("content","java.lang.String",e);
        }
        try {
            // Omitting unrecognized type java.lang.Object for column fts!
        } catch (java.lang.ClassCastException e) {
            throw new UnexpectedJsonValueType("fts","java.lang.Object",e);
        }
        return this;
    }


    @Override
    public default io.vertx.core.json.JsonObject toJson() {
        io.vertx.core.json.JsonObject json = new io.vertx.core.json.JsonObject();
        json.put("id",getId());
        json.put("title",getTitle());
        json.put("content",getContent());
        // Omitting unrecognized type java.lang.Object for column fts!
        return json;
    }

}
