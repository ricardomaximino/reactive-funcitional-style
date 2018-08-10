package com.brasajava.routerfunctionstyle.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.brasajava.routerfunctionstyle.converter.LeadConverter;
import com.brasajava.routerfunctionstyle.domain.LeadDTO;
import com.brasajava.routerfunctionstyle.service.LeadService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LeadHandler {

  private static final String ID_PARAM = "id";
  @Autowired private LeadService service;
  @Autowired private LeadConverter converter;

  public Mono<ServerResponse> hello(ServerRequest request) {
    log.debug("HELLO FROM HANDLER");
    return ServerResponse.ok().body(BodyInserters.fromObject("Hello World"));
  }

  public Mono<ServerResponse> findAll(ServerRequest request) {
    log.debug("FIND ALL FROM HANDLER");
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.findAllLeads().map(converter::toLeadDto), LeadDTO.class);
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    log.debug("FIND BY ID FROM HANDLER");
    return service
        .findLeadById(request.pathVariable(ID_PARAM))
        .flatMap(
            lead ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(converter.toLeadDto(lead))))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    log.debug("CREATE FROM HANDLER");
    return request
        .bodyToMono(LeadDTO.class)
        .flatMap(
            dto -> {
              return service
                  .create(converter.toLead(dto))
                  .flatMap(
                      lead -> {
                        return ServerResponse.status(HttpStatus.CREATED)
                            .body(BodyInserters.fromObject(converter.toLeadIdDto(lead)));
                      });
            });
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    log.debug("UPDATE FROM HANDLER");
    return request
        .bodyToMono(LeadDTO.class)
        .flatMap(
            dto -> {
              return service
                  .update(request.pathVariable(ID_PARAM), converter.toLead(dto))
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
    log.debug("UPDATE WITH PATCH FROM HANDLER");
    return request
        .bodyToMono(LeadDTO.class)
        .flatMap(
            dto -> {
              return service
                  .update(request.pathVariable(ID_PARAM), converter.toLead(dto))
                  .flatMap(
                      lead -> {
                        return ServerResponse.status(HttpStatus.NO_CONTENT).build();
                      });
            })
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deleteById(ServerRequest request) {
    log.debug("DELETE FROM HANDLER");
    return service
        .deleteLeadById(request.pathVariable(ID_PARAM))
        .flatMap(d -> ServerResponse.noContent().build())
        .switchIfEmpty(ServerResponse.noContent().build());
  }
}
