package com.brasajava.routerfunctionstyle.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class Lead {

  @Id private String id;
  private String name;
  private String lastname;
}
