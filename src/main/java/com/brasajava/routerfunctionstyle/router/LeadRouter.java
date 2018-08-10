package com.brasajava.routerfunctionstyle.router;

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

import com.brasajava.routerfunctionstyle.filter.GlobalHandlerFilterFunction;
import com.brasajava.routerfunctionstyle.handler.LeadHandler;

@Configuration
public class LeadRouter {
  @Autowired private LeadHandler handler;

  @Bean
  public RouterFunction<ServerResponse> helloRouter() {
    return RouterFunctions.route(RequestPredicates.GET("/hello"), handler::hello);
  }

  @Bean
  public HandlerFilterFunction<ServerResponse, ServerResponse> handlerFilter() {
    return new GlobalHandlerFilterFunction();
  }

  @Bean
  public RouterFunction<ServerResponse> leadsRouter() {
    return nest(
        path("/lead"),
        route(GET("/{id}"), handler::findById)
            .filter(handlerFilter())
            .andRoute(GET("/"), handler::findAll)
            .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::create)
            .andRoute(PUT("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::update)
            .andRoute(
                PATCH("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::updateWithPatch)
            .andRoute(
                DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteById));
  }
}
