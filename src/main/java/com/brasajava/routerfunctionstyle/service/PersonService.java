package com.brasajava.routerfunctionstyle.service;

import com.brasajava.routerfunctionstyle.domain.entity.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

  Mono<Person> create(Person person, String user);

  Mono<Person> update(String id, Person person, String user);

  Mono<Void> deleteById(String id, String user);

  Flux<Person> findAll();

  Mono<Person> findById(String id);
}
