package com.brasajava.routerfunctionstyle.message.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PersonSenderChannelBinding {

	public static final String SEND_PERSON_CREATED_EVENT = "send-person-created-event";
	public static final String SEND_PERSON_UPDATED_EVENT = "send-person-updated-event";
	public static final String SEND_PERSON_DELETED_EVENT = "send-person-deleted-event";
	
	@Output(SEND_PERSON_CREATED_EVENT)
	MessageChannel created();
	
	@Output(SEND_PERSON_UPDATED_EVENT)
	MessageChannel updated();
	
	@Output(SEND_PERSON_DELETED_EVENT)
	MessageChannel deleted();
}
