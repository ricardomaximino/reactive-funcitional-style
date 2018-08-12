package com.brasajava.routerfunctionstyle.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.brasajava.routerfunctionstyle.domain.entity.Person;
import com.brasajava.routerfunctionstyle.domain.repository.PersonRepository;
import com.brasajava.routerfunctionstyle.message.model.Event;
import com.brasajava.routerfunctionstyle.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

  private PersonRepository repository;
private ApplicationEventPublisher eventPublisher;
  
  public PersonServiceImpl(PersonRepository repository, ApplicationEventPublisher eventPublisher) {
	  this.repository = repository;
	  this.eventPublisher = eventPublisher;
  }

  @Override
  public Mono<Person> create(Person person, String user) {
    person.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    return repository.save(onCreate(person, user));
  }

  @Override
  public Mono<Person> update(String id, Person lead, String user) {
    return repository
        .findById(id)
        .flatMap(
            l -> {
              return repository.save(onUpdate(l,lead, user));
            });
  }
  
  @Override
  public Mono<Void> deleteLeadById(String id, String user) {
    return repository.findById(id).flatMap(person -> {
    	return repository.delete(person).doOnSuccess(v -> afterDelete(person, user));
    });
  }

  @Override
  public Mono<Person> updateWithPatch(String id, Object objectPatch, String user) {
    return null;
  }

  @Override
  public Flux<Person> findAllLeads() {
    return repository.findAll();
  }

  @Override
  public Mono<Person> findLeadById(String id) {
    return repository.findById(id);
  }
  
  private Person onCreate(Person person, String user) {
	  person.create(createEvent(Event.CREATED_EVENT, person.getId(), user));
	  return person;
  }
  
  private Person onUpdate(Person personDB, Person person, String user) {
	  person.setId(personDB.getId());
	  person.update(createEvent(Event.UPDATED_EVENT, person.getId(), user));
	  return person;
  }
  
  private void afterDelete(Person person, String user) {
	  eventPublisher.publishEvent(createEvent(Event.DELETED_EVENT, person.getId(), user));
	  person = null;	  
  }
  
  private Event createEvent(String type, String key, String user) {
	  return new Event(UUID.randomUUID().toString(), type, key, user, new Date().getTime());
  }
}
