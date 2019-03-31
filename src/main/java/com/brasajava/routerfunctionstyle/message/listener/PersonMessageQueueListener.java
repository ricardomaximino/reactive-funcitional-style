package com.brasajava.routerfunctionstyle.message.listener;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

import com.brasajava.routerfunctionstyle.message.model.Event;
import com.brasajava.routerfunctionstyle.message.model.FindPersonEvent;
import com.brasajava.routerfunctionstyle.message.sender.PersonMessageQueueSender;
import com.brasajava.routerfunctionstyle.service.PersonService;

import reactor.core.publisher.Flux;

@EnableAutoConfiguration
@EnableBinding(Sink.class)
public class PersonMessageQueueListener {
  private static final Logger LOG = LoggerFactory.getLogger(PersonMessageQueueListener.class);
  private PersonService service;
  private PersonMessageQueueSender sender;

  public PersonMessageQueueListener(PersonService service, PersonMessageQueueSender sender) {
    this.service = service;
    this.sender = sender;
  }

  @StreamListener(Sink.INPUT)
  public void userEvent(@Payload Flux<Event> fluxMessage) {
    fluxMessage.subscribe(
        event -> {
          LOG.info("USER EVENT RECEIVED");
          service
              .findById(event.getKey())
              .map(
                  p ->
                      new FindPersonEvent(
                          UUID.randomUUID().toString(),
                          event.getKey(),
                          event.getOperator(),
                          new Date().getTime(),
                          p,
                          "found"))
              .defaultIfEmpty(
                  new FindPersonEvent(
                      UUID.randomUUID().toString(),
                      event.getKey(),
                      event.getOperator(),
                      new Date().getTime(),
                      null,
                      "not-found"))
              .doOnNext(
                  e ->
                      sender.sendEvent(
                          MessageBuilder.withPayload((Event) e)
                              .setHeader("routingKey", "brasajava.person.search.response")
                              .build()));
        },
        error -> LOG.info("ERROR ON RECEIVE USER EVENT"),
        () -> LOG.info("USER EVENT RECEIVED COMPLETE"));
  }
}
