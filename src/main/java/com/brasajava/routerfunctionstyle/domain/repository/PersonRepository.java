package com.brasajava.routerfunctionstyle.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.brasajava.routerfunctionstyle.domain.entity.Person;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {}
