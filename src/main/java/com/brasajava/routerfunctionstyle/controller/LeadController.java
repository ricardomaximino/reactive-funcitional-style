package com.brasajava.routerfunctionstyle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brasajava.routerfunctionstyle.converter.LeadConverter;
import com.brasajava.routerfunctionstyle.domain.LeadDTO;
import com.brasajava.routerfunctionstyle.domain.LeadIDDTO;
import com.brasajava.routerfunctionstyle.service.LeadService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller")
@Slf4j
public class LeadController {
  @Autowired private LeadService leadService;
  @Autowired private LeadConverter leadConverter;

  @GetMapping("/hello")
  public Mono<String> hello() {
    log.info("HELLO FROM CONTROLLER");
    return Mono.just("Hello World from LeadController");
  }

  @GetMapping("/lead")
  @ResponseStatus(HttpStatus.OK)
  public Flux<LeadDTO> getAllLead() {
    log.debug("FIND ALL FROM CONTROLLER");
    return leadService.findAllLeads().map(leadConverter::toLeadDto);
  }

  @GetMapping("/lead/{id}")
  public Mono<ResponseEntity<LeadDTO>> getLeadById(@PathVariable("id") String id) {
    log.debug("FIND BY ID FROM CONTROLLER");
    return leadService
        .findLeadById(id)
        .map(l -> ResponseEntity.ok(leadConverter.toLeadDto(l)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/lead")
  public Mono<ResponseEntity<LeadIDDTO>> createLead(@Valid @RequestBody LeadDTO leadDTO) {
    log.debug("CREATE FROM CONTROLLER");
    return leadService
        .create(leadConverter.toLead(leadDTO))
        .map(l -> new ResponseEntity<>(leadConverter.toLeadIdDto(l), HttpStatus.CREATED));
  }

  @PutMapping("/lead/{id}")
  public Mono<ResponseEntity<String>> updateLead(
      @Valid @RequestBody LeadDTO leadDTO, @PathVariable("id") String id) {
    log.debug("UPDATE FROM CONTROLLER");
    return leadService
        .update(id, leadConverter.toLead(leadDTO))
        .map(l -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/lead/{id}")
  public Mono<ResponseEntity<String>> updateLeadWithPatch(
      @PathVariable("id") String id, @RequestBody Object objectPatch) {
    log.debug("UPDATE WITH PATCH FROM CONTROLLER");
    return leadService
        .updateWithPatch(id, objectPatch)
        .map(l -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/lead/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteLeadById(@PathVariable("id") String id) {
    log.debug("DELETE FROM CONTROLLER");
    return leadService.deleteLeadById(id);
  }
}
