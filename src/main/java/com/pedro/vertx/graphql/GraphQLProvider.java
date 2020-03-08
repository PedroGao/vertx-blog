package com.pedro.vertx.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
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

  private GraphQLDataFetchers graphQLDataFetchers;

  public GraphQL graphQL() {
    return graphQL;
  }

  public GraphQLProvider(GraphQLDataFetchers graphQLDataFetchers) throws IOException {
    this.graphQLDataFetchers = graphQLDataFetchers;
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
        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher())
        .dataFetcher("articleById",graphQLDataFetchers.getArticleByIdDataFetcher())
      )
      .type(newTypeWiring("Book")
        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher())
        .dataFetcher("pageCount", graphQLDataFetchers.getPageCountDataFetcher()))
      .build();
  }

}
