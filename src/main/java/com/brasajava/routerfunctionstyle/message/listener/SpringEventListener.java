package com.brasajava.routerfunctionstyle.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.message.model.Event;
import com.brasajava.routerfunctionstyle.message.sender.PersonMessageQueueSender;

@Component
public class SpringEventListener {
	private static final Logger LOG = LoggerFactory.getLogger(SpringEventListener.class);

	private PersonMessageQueueSender sender;

	public SpringEventListener(PersonMessageQueueSender sender) {
		this.sender = sender;
	}

	@EventListener
	public void eventListener(@Payload Event event) {

		switch (event.getType()) {
		case Event.CREATED_EVENT:
			LOG.info("Received Spring Created Event");
			sender.sendCreatedEvent(createMessage(event));
			break;
		case Event.UPDATED_EVENT:
			LOG.info("Received Spring Updated Event");
			sender.sendUpdatedEvent(createMessage(event));
			break;
		case Event.DELETED_EVENT:
			LOG.info("Received Spring Deleted Event");
			sender.sendDeletedEvent(createMessage(event));
			break;
		default:
			LOG.info("Received Unknown Spring Event");
			break;
		}

	}
	
	private Message<Event> createMessage(Event event){
		return MessageBuilder.withPayload(event).build();
	}

}
