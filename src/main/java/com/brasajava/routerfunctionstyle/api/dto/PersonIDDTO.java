package com.brasajava.routerfunctionstyle.api.dto;

public class PersonIDDTO {
  private String personId;

  public PersonIDDTO() {}

  public PersonIDDTO(String personId) {
    super();
    this.personId = personId;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  @Override
  public String toString() {
    return "LeadIDDTO [personId=" + personId + "]";
  }
}
