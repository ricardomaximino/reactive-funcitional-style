package com.brasajava.routerfunctionstyle.api.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.api.dto.PersonDTO;
import com.brasajava.routerfunctionstyle.api.dto.PersonIDDTO;
import com.brasajava.routerfunctionstyle.domain.entity.Person;

@Component
public class PersonConverter {

  public PersonDTO toPersonDto(Person person) {
    PersonDTO dto = new PersonDTO();
    BeanUtils.copyProperties(person, dto);
    return dto;
  }

  public Person toPerson(PersonDTO dto) {
    Person person = new Person();
    BeanUtils.copyProperties(dto, person);
    return person;
  }

  public PersonIDDTO toPersonIdDto(Person person) {
    PersonIDDTO personIDDTO = new PersonIDDTO();
    personIDDTO.setPersonId(person.getId());
    return personIDDTO;
  }
}
