package com.brasajava.routerfunctionstyle.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.message.model.Event;
import com.brasajava.routerfunctionstyle.message.model.UpdatePersonEvent;
import com.brasajava.routerfunctionstyle.message.sender.PersonMessageQueueSender;

@Component
public class SpringUpdateEventListener {
  private static final Logger LOG = LoggerFactory.getLogger(SpringUpdateEventListener.class);

  private PersonMessageQueueSender sender;

  public SpringUpdateEventListener(PersonMessageQueueSender sender) {
    this.sender = sender;
  }

  @EventListener
  public void eventListener(@Payload UpdatePersonEvent event) {
    LOG.info("Received Spring Updated Event");
    sender.sendEvent(createMessage(event));
  }

  private Message<Event> createMessage(Event event) {
    return MessageBuilder.withPayload(event)
        .setHeader("routingKey", Event.UPDATED_ROUTING_KEY)
        .build();
  }
}
