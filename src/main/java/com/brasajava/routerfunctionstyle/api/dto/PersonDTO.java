package com.brasajava.routerfunctionstyle.api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonDTO {

  private String id;
  private String name;
  private String lastname;
  private LocalDate birthday;
}
