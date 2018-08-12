package com.brasajava.routerfunctionstyle.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import com.brasajava.routerfunctionstyle.message.channel.PersonReciverChannelBinding;

import reactor.core.publisher.Flux;

@EnableAutoConfiguration
@EnableBinding(PersonReciverChannelBinding.class)
public class PersonMessageQueueListener {
	private static final Logger LOG = LoggerFactory.getLogger(PersonMessageQueueListener.class);
	
	@StreamListener(PersonReciverChannelBinding.LISTEN_PERSON_CREATED_EVENT)
	public void createdEvent(Flux<Message<?>> fluxMessage) {
		fluxMessage.subscribe(message -> {
			LOG.info("PERSON CREATED EVENT RECEIVED");
		}, error -> {
			LOG.info("ERROR ON RECEIVE PERSON CREATED EVENT");
		}, () -> {
			LOG.info("PERSON CREATED EVENT RECEIVED COMPLETE");
		});
	}
	
	@StreamListener(PersonReciverChannelBinding.LISTEN_PERSON_UPDATED_EVENT)
	public void updatedEvent(Flux<Message<?>> fluxMessage) {
		fluxMessage.subscribe(message -> {
			LOG.info("PERSON UPDATED EVENT RECEIVED");
		}, error -> {
			LOG.info("ERROR ON RECEIVE PERSON UPDATED EVENT");
		}, () -> {
			LOG.info("PERSON UPDATED EVENT RECEIVED COMPLETE");
		});
	}
	
	@StreamListener(PersonReciverChannelBinding.LISTEN_PERSON_DELETED_EVENT)
	public void deletedEvent(Flux<Message<?>> fluxMessage) {
		fluxMessage.subscribe(message -> {
			LOG.info("PERSON DELETED EVENT RECEIVED");
		}, error -> {
			LOG.info("ERROR ON RECEIVE PERSON DELETED EVENT");
		}, () -> {
			LOG.info("PERSON DELETED EVENT RECEIVED COMPLETE");
		});
	}

}
