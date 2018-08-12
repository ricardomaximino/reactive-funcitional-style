package com.brasajava.routerfunctionstyle.message.model;

public class Event {
	
	public static final String CREATED_EVENT = "created";
	public static final String UPDATED_EVENT = "updated";
	public static final String DELETED_EVENT = "deleted";
	
	private String id;
	private String type;
	private String  key;
	private String user;
	private Long creationDate;
	
	
	public Event() {}
	
	
	public Event(String id, String type, String key, String user, Long creationDate) {
		super();
		this.id = id;
		this.type = type;
		this.key = key;
		this.user = user;
		this.creationDate = creationDate;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}
	public static String getCreatedEvent() {
		return CREATED_EVENT;
	}
	public static String getUpdatedEvent() {
		return UPDATED_EVENT;
	}
	public static String getDeletedEvent() {
		return DELETED_EVENT;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", type=" + type + ", key=" + key + ", user=" + user + ", creationDate="
				+ creationDate + "]";
	}
	
	
	

}
