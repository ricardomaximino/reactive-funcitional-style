package com.brasajava.routerfunctionstyle.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.brasajava.routerfunctionstyle.api.filter.CheckHeadersHandlerFilter;
import com.brasajava.routerfunctionstyle.api.handler.PersonHandler;

@Configuration
public class PersonRouter {
  @Autowired private PersonHandler handler;

  @Bean
  public RouterFunction<ServerResponse> helloRouter() {
    return RouterFunctions.route(RequestPredicates.GET("/hello"), handler::hello);
  }

  @Bean
  public HandlerFilterFunction<ServerResponse, ServerResponse> handlerFilter() {
    return new CheckHeadersHandlerFilter();
  }

  // @formatter:off
  /*
   * Next find out how to configure a filter to all routers
   */
  @Bean
  public RouterFunction<ServerResponse> leadsRouter() {
    return nest(
            path("/functional/person"),
            route(GET("/hello/{name}"), handler::helloClient)
                .filter(handlerFilter())
                .andRoute(GET("/hello"), handler::hello)
                .andRoute(GET("/{id}"), handler::findById)
                .andRoute(GET("/"), handler::findAll)
                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .filter(handlerFilter())
                .andRoute(PUT("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::update)
                .filter(handlerFilter())
                .andRoute(
                    PATCH("/{id}").and(accept(MediaType.APPLICATION_JSON)),
                    handler::updateWithPatch)
                .filter(handlerFilter())
                .andRoute(
                    DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteById))
        .filter(handlerFilter());
  }
  // @formatter:on

}
