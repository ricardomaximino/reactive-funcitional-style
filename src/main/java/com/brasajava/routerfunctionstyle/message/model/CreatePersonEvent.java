package com.brasajava.routerfunctionstyle.message.model;

public class CreatePersonEvent extends Event {

  public CreatePersonEvent() {
    super();
  }

  public CreatePersonEvent(String id, String key, String user, Long creationDate, Object object) {
    super(id, "person", "created", key, user, creationDate, object);
  }
}
