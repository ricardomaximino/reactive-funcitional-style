package com.brasajava.routerfunctionstyle.message.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PersonReciverChannelBinding {
	
	public static final String LISTEN_PERSON_CREATED_EVENT = "listen-person-created-event";
	public static final String LISTEN_PERSON_UPDATED_EVENT = "listen-person-updated-event";
	public static final String LISTEN_PERSON_DELETED_EVENT = "listen-person-deleted-event";
	
	@Input(LISTEN_PERSON_CREATED_EVENT)
	SubscribableChannel created();
	
	
	@Input(LISTEN_PERSON_UPDATED_EVENT)
	SubscribableChannel updated();
	
	@Input(LISTEN_PERSON_DELETED_EVENT)
	SubscribableChannel deleted();

}
