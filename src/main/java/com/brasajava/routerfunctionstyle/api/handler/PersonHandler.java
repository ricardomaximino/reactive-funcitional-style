package com.brasajava.routerfunctionstyle.api.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.brasajava.routerfunctionstyle.api.converter.PersonConverter;
import com.brasajava.routerfunctionstyle.api.dto.PersonDTO;
import com.brasajava.routerfunctionstyle.service.PersonService;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {
	private static final Logger LOG = LoggerFactory.getLogger(PersonHandler.class);

  private static final String ID_PARAM = "id";
  private static final String X_USER = "X-User";
 
  private PersonService service;
  private PersonConverter converter;
  
  public PersonHandler(PersonService service, PersonConverter converter) {
	  this.service = service;
	  this.converter = converter;
  }

  public Mono<ServerResponse> hello(ServerRequest request) {
    LOG.debug("HELLO FROM HANDLER");
    return ServerResponse.ok().body(BodyInserters.fromObject("Hello World"));
  }

  public Mono<ServerResponse> findAll(ServerRequest request) {
    LOG.debug("FIND ALL FROM HANDLER");
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.findAll().map(converter::toPersonDto), PersonDTO.class);
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    LOG.debug("FIND BY ID FROM HANDLER");
    return service
        .findById(request.pathVariable(ID_PARAM))
        .flatMap(
            lead ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(converter.toPersonDto(lead))))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    LOG.debug("CREATE FROM HANDLER");
    return request
        .bodyToMono(PersonDTO.class)
        .flatMap(
            dto -> {
              return service
                  .create(converter.toPerson(dto), request.headers().header(X_USER).get(0))
                  .flatMap(
                      lead -> {
                        return ServerResponse.status(HttpStatus.CREATED)
                            .body(BodyInserters.fromObject(converter.toPersonIdDto(lead)));
                      });
            });
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    LOG.debug("UPDATE FROM HANDLER");
    return request
        .bodyToMono(PersonDTO.class)
        .flatMap(
            dto -> {
              return service
                  .update(request.pathVariable(ID_PARAM), converter.toPerson(dto), request.headers().header(X_USER).get(0))
                  .flatMap(
                      lead -> {
                        return ServerResponse.status(HttpStatus.NO_CONTENT).build();
                      });
            })
        .switchIfEmpty(ServerResponse.notFound().build())
        .onErrorResume(
            e ->
                Mono.error(
                    Exceptions.propagate(
                        new ResponseStatusException(HttpStatus.ALREADY_REPORTED))));
  }

  public Mono<ServerResponse> updateWithPatch(ServerRequest request) {
    LOG.debug("UPDATE WITH PATCH FROM HANDLER");
    return request
        .bodyToMono(PersonDTO.class)
        .flatMap(
            dto -> {
              return service
                  .update(request.pathVariable(ID_PARAM), converter.toPerson(dto), request.headers().header(X_USER).get(0))
                  .flatMap(
                      lead -> {
                        return ServerResponse.status(HttpStatus.NO_CONTENT).build();
                      });
            })
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deleteById(ServerRequest request) {
    LOG.debug("DELETE FROM HANDLER");
    return service
        .deleteById(request.pathVariable(ID_PARAM), request.headers().header(X_USER).get(0))
        .flatMap(d -> ServerResponse.noContent().build())
        .switchIfEmpty(ServerResponse.noContent().build());
  }
}
