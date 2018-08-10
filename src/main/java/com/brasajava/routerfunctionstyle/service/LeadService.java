package com.brasajava.routerfunctionstyle.service;

import com.brasajava.routerfunctionstyle.domain.Lead;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LeadService {

  Mono<Lead> create(Lead lead);

  Mono<Lead> update(String id, Lead lead);

  Mono<Lead> updateWithPatch(String id, Object objectPatch);

  Flux<Lead> findAllLeads();

  Mono<Lead> findLeadById(String id);

  Mono<Void> deleteLeadById(String id);
}
