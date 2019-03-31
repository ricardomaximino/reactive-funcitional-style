package com.brasajava.routerfunctionstyle.message.model;

public class DeletePersonEvent extends Event {

  public DeletePersonEvent() {
    super();
  }

  public DeletePersonEvent(String id, String key, String user, Long creationDate, Object object) {
    super(id, "person", "deleted", key, user, creationDate, object);
  }
}
