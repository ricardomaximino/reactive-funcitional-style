package com.brasajava.routerfunctionstyle.api.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.api.dto.PersonDTO;
import com.brasajava.routerfunctionstyle.api.dto.PersonIDDTO;
import com.brasajava.routerfunctionstyle.domain.entity.Person;


@Component
public class PersonConverter {

  public PersonDTO toLeadDto(Person lead) {
    PersonDTO dto = new PersonDTO();
    BeanUtils.copyProperties(lead, dto);
    return dto;
  }

  public Person toLead(PersonDTO dto) {
    Person lead = new Person();
    BeanUtils.copyProperties(dto, lead);
    return lead;
  }

  public PersonIDDTO toLeadIdDto(Person lead) {
    PersonIDDTO id = new PersonIDDTO();
    BeanUtils.copyProperties(lead, id);
    return id;
  }
}
