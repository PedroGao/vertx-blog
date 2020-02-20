package com.pedro.vertx.database;

import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

public class JooqConfiguration {

  public static final Configuration configuration = new DefaultConfiguration().set(SQLDialect.POSTGRES);
}
