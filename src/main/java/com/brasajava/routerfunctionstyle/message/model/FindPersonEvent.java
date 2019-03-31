package com.brasajava.routerfunctionstyle.message.model;

public class FindPersonEvent extends Event {

  public FindPersonEvent() {
    super();
  }

  public FindPersonEvent(
      String id, String key, String user, Long creationDate, Object object, String action) {
    super(id, "person", action, key, user, creationDate, object);
  }
}
