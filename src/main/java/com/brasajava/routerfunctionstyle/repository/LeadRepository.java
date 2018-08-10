package com.brasajava.routerfunctionstyle.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.brasajava.routerfunctionstyle.domain.Lead;

public interface LeadRepository extends ReactiveMongoRepository<Lead, String> {}
