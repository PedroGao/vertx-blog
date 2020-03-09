package com.pedro.vertx.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.pedro.vertx.graphql.datafetcher.ArticleDataFetcher;
import com.pedro.vertx.graphql.datafetcher.UserDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@SuppressWarnings("UnstableApiUsage")
public class GraphQLProvider {
  private GraphQL graphQL;

  private ArticleDataFetcher articleDataFetcher;
  private UserDataFetcher userDataFetcher;

  public GraphQL graphQL() {
    return graphQL;
  }

  public GraphQLProvider(ArticleDataFetcher articleDataFetcher, UserDataFetcher userDataFetcher) throws IOException {
    this.articleDataFetcher = articleDataFetcher;
    this.userDataFetcher = userDataFetcher;
    URL url = Resources.getResource("schema.graphqls");
    String sdl = Resources.toString(url, Charsets.UTF_8);
    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    RuntimeWiring runtimeWiring = buildWiring();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
      .type(newTypeWiring("Query")
        .dataFetcher("getArticleById", articleDataFetcher.getArticleByIdDataFetcher())
        .dataFetcher("searchArticles", articleDataFetcher.searchArticlesDataFetcher())
        .dataFetcher("getUserById", userDataFetcher.getUserByIdDataFetcher())
      )
      .type(newTypeWiring("Mutation")
        .dataFetcher("login",userDataFetcher.loginDataFetcher())
      )
      .build();
  }

}
