package com.brasajava.routerfunctionstyle.api.filter;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.brasajava.routerfunctionstyle.api.dto.ErrorMessageDTO;
import com.brasajava.routerfunctionstyle.exception.ServiceException;

import reactor.core.publisher.Mono;

public class CheckHeadersHandlerFilter
    implements HandlerFilterFunction<ServerResponse, ServerResponse> {
  private static final String X_USER = "X-User";

  public CheckHeadersHandlerFilter() {}

  @Override
  public Mono<ServerResponse> filter(
      ServerRequest request, HandlerFunction<ServerResponse> handlerFunction) {
    if (request.headers().header(X_USER) == null || request.headers().header(X_USER).isEmpty()) {
      RuntimeException throwable =
          new ServiceException("Missing Required Header << " + X_USER + ">>");
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorMessageDTO body =
          new ErrorMessageDTO(
              LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
              httpStatus.value(),
              httpStatus.getReasonPhrase(),
              throwable.getClass().getName(),
              throwable.getMessage());

      return ServerResponse.badRequest().body(BodyInserters.fromObject(body));
    }
    return handlerFunction.handle(request);
  }
}
