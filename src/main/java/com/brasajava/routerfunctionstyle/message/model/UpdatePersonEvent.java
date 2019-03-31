package com.brasajava.routerfunctionstyle.message.model;

public class UpdatePersonEvent extends Event {

  public UpdatePersonEvent() {
    super();
  }

  public UpdatePersonEvent(String id, String key, String user, Long creationDate, Object object) {
    super(id, "person", "updated", key, user, creationDate, object);
  }
}
