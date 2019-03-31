package com.brasajava.routerfunctionstyle.message.model;

public class Event {

  public static final String CREATED_EVENT = "created";
  public static final String UPDATED_EVENT = "updated";
  public static final String DELETED_EVENT = "deleted";
  public static final String CREATED_ROUTING_KEY = "brasajava.person.created";
  public static final String UPDATED_ROUTING_KEY = "brasajava.person.updated";
  public static final String DELETED_ROUTING_KEY = "brasajava.person.deleted";

  private String id;
  private String type;
  private String action;
  private String key;
  private String operator;
  private Long creationDate;
  private Object object;

  public Event() {}

  public Event(
      String id,
      String type,
      String action,
      String key,
      String user,
      Long creationDate,
      Object object) {
    this.id = id;
    this.type = type;
    this.action = action;
    this.key = key;
    this.operator = user;
    this.creationDate = creationDate;
    this.setObject(object);
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

  public String getOperator() {
    return operator;
  }

  public void setOperator(String user) {
    this.operator = user;
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

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @Override
  public String toString() {
    return "Event [id="
        + id
        + ", type="
        + type
        + ", action="
        + action
        + ", key="
        + key
        + ", operator="
        + operator
        + ", creationDate="
        + creationDate
        + ", object="
        + object
        + "]";
  }
}
