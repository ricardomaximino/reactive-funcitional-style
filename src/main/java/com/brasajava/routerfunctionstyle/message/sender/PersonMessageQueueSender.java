package com.brasajava.routerfunctionstyle.message.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.messaging.Message;

import com.brasajava.routerfunctionstyle.message.channel.PersonSenderChannelBinding;
import com.brasajava.routerfunctionstyle.message.model.Event;

import reactor.core.publisher.Flux;

@EnableAutoConfiguration
@EnableBinding(PersonSenderChannelBinding.class)
public class PersonMessageQueueSender {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersonMessageQueueSender.class);
	private FluxSender createdFluxSender;
	private FluxSender updatedFluxSender;
	private FluxSender deletedFluxSender;
	
	@StreamEmitter
	public void setCreatedFluxSender(@Output(PersonSenderChannelBinding.SEND_PERSON_CREATED_EVENT) FluxSender createdFluxSender) {
		this.createdFluxSender = createdFluxSender;
	}
	
	@StreamEmitter
	public void setUpdatedFluxSender(@Output(PersonSenderChannelBinding.SEND_PERSON_UPDATED_EVENT) FluxSender updatedFluxSender) {
		this.updatedFluxSender = updatedFluxSender;
	}
	
	@StreamEmitter
	public void setDeletedFluxSender(@Output(PersonSenderChannelBinding.SEND_PERSON_DELETED_EVENT) FluxSender deletedFluxSender) {
		this.deletedFluxSender = deletedFluxSender;
	}
	
	public void sendCreatedEvent(Message<Event> message) {
		this.createdFluxSender.send(Flux.just(message)).subscribe(s -> {
			LOG.info("PERSON CREATED EVENT SENT");
		}, error -> {
			LOG.info("ERROR ON SEND PERSON CREATED EVENT");
		}, () -> {
			LOG.info("PERSON CREATED EVENT SENT COMPLETE");
		});;
	}
	
	public void sendUpdatedEvent(Message<Event> message) {
		this.updatedFluxSender.send(Flux.just(message)).subscribe(s -> {
			LOG.info("PERSON UPDATED EVENT SENT");
		}, error -> {
			LOG.info("ERROR ON SEND PERSON UPDATED EVENT");
		}, () -> {
			LOG.info("PERSON UPDATED EVENT SENT COMPLETE");
		});;
	}
	
	public void sendDeletedEvent(Message<Event> message) {
		this.deletedFluxSender.send(Flux.just(message)).subscribe(s -> {
			LOG.info("PERSON DELETED EVENT SENT");
		}, error -> {
			LOG.info("ERROR ON SEND PERSON DELETED EVENT");
		}, () -> {
			LOG.info("PERSON DELETED EVENT SENT COMPLETE");
		});;
	}
	
	
	

}
