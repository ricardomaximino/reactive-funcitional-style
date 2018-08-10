package com.brasajava.routerfunctionstyle.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brasajava.routerfunctionstyle.domain.Lead;
import com.brasajava.routerfunctionstyle.exception.LeadNotFoundException;
import com.brasajava.routerfunctionstyle.repository.LeadRepository;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LeadServiceImpl implements LeadService {

  @Autowired private LeadRepository repository;

  @Override
  public Mono<Lead> create(Lead lead) {
    lead.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    return repository.save(lead);
  }

  @Override
  public Mono<Lead> update(String id, Lead lead) {
    return repository
        .findById(id)
        .flatMap(
            l -> {
              if (l.getName().equals("Error")) {
                throw Exceptions.propagate(
                    new LeadNotFoundException(
                        "Service Boom because the lead with id: " + id + " not found."));
              }
              System.out.println("Existe => " + l);
              lead.setId(l.getId());
              return repository.save(lead);
            });
  }

  @Override
  public Mono<Lead> updateWithPatch(String id, Object objectPatch) {
    return null;
  }

  @Override
  public Flux<Lead> findAllLeads() {
    return repository.findAll();
  }

  @Override
  public Mono<Lead> findLeadById(String id) {
    return repository.findById(id);
  }

  @Override
  public Mono<Void> deleteLeadById(String id) {
    return repository.deleteById(id);
  }
}
