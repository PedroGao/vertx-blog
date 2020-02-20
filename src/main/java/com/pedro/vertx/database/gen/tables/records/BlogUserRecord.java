/*
 * This file is generated by jOOQ.
 */
package com.pedro.vertx.database.gen.tables.records;


import com.pedro.vertx.database.gen.tables.BlogUser;
import com.pedro.vertx.database.gen.tables.interfaces.IBlogUser;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class BlogUserRecord extends UpdatableRecordImpl<BlogUserRecord> implements VertxPojo, Record3<Integer, String, String>, IBlogUser {

    private static final long serialVersionUID = -971191923;

    /**
     * Setter for <code>public.blog_user.id</code>.
     */
    @Override
    public BlogUserRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.blog_user.id</code>.
     */
    @Override
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.blog_user.username</code>.
     */
    @Override
    public BlogUserRecord setUsername(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.blog_user.username</code>.
     */
    @Override
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.blog_user.password</code>.
     */
    @Override
    public BlogUserRecord setPassword(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.blog_user.password</code>.
     */
    @Override
    public String getPassword() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return BlogUser.BLOG_USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return BlogUser.BLOG_USER.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return BlogUser.BLOG_USER.PASSWORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogUserRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogUserRecord value2(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogUserRecord value3(String value) {
        setPassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogUserRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void from(IBlogUser from) {
        setId(from.getId());
        setUsername(from.getUsername());
        setPassword(from.getPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends IBlogUser> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BlogUserRecord
     */
    public BlogUserRecord() {
        super(BlogUser.BLOG_USER);
    }

    /**
     * Create a detached, initialised BlogUserRecord
     */
    public BlogUserRecord(Integer id, String username, String password) {
        super(BlogUser.BLOG_USER);

        set(0, id);
        set(1, username);
        set(2, password);
    }

    public BlogUserRecord(io.vertx.core.json.JsonObject json) {
        this();
        fromJson(json);
    }
}