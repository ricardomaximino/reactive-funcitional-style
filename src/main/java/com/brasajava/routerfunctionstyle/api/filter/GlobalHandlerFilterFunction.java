package com.brasajava.routerfunctionstyle.api.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class GlobalHandlerFilterFunction
    implements HandlerFilterFunction<ServerResponse, ServerResponse> {
	
	

  public GlobalHandlerFilterFunction() {}

@Override
  public Mono<ServerResponse> filter(
      ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) {
    if (serverRequest.pathVariable("id").equals("123")) {
      return ServerResponse.status(HttpStatus.FORBIDDEN).build();
    }
    return handlerFunction.handle(serverRequest);
  }
}
