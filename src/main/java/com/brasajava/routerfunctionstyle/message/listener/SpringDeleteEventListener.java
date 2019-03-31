package com.brasajava.routerfunctionstyle.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.message.model.DeletePersonEvent;
import com.brasajava.routerfunctionstyle.message.model.Event;
import com.brasajava.routerfunctionstyle.message.sender.PersonMessageQueueSender;

@Component
public class SpringDeleteEventListener {
  private static final Logger LOG = LoggerFactory.getLogger(SpringDeleteEventListener.class);

  private PersonMessageQueueSender sender;

  public SpringDeleteEventListener(PersonMessageQueueSender sender) {
    this.sender = sender;
  }

  @EventListener
  public void eventListener(@Payload DeletePersonEvent event) {
    LOG.info("Received Spring Deleted Event");
    sender.sendEvent(createMessage(event));
  }

  private Message<Event> createMessage(Event event) {
    return MessageBuilder.withPayload(event)
        .setHeader("routingKey", Event.DELETED_ROUTING_KEY)
        .build();
  }
}
