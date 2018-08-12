package com.brasajava.routerfunctionstyle.api.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.brasajava.routerfunctionstyle.api.converter.PersonConverter;
import com.brasajava.routerfunctionstyle.api.dto.PersonDTO;
import com.brasajava.routerfunctionstyle.api.dto.PersonIDDTO;
import com.brasajava.routerfunctionstyle.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller")
public class PersonController {
	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);
  @Autowired private PersonService personService;
  @Autowired private PersonConverter personConverter;

  @GetMapping("/hello")
  public Mono<String> hello(Mono<Principal> principal) {
    LOG.info("HELLO FROM CONTROLLER");
    return principal.map(Principal::getName).map(name -> String.format("Hello, %s", name));
  }

  @GetMapping("/person")
  @ResponseStatus(HttpStatus.OK)
  public Flux<PersonDTO> getAllLead() {
    LOG.debug("FIND ALL FROM CONTROLLER");
    return personService.findAllLeads().map(personConverter::toLeadDto);
  }

  @GetMapping("/person/{id}")
  public Mono<ResponseEntity<PersonDTO>> getLeadById(@PathVariable("id") String id) {
    LOG.debug("FIND BY ID FROM CONTROLLER");
    return personService
        .findLeadById(id)
        .map(l -> ResponseEntity.ok(personConverter.toLeadDto(l)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/person")
  public Mono<ResponseEntity<PersonIDDTO>> createPerson(@Valid @RequestBody PersonDTO leadDTO, @RequestHeader("X-User") String user) {
    LOG.debug("CREATE FROM CONTROLLER");
    return personService
        .create(personConverter.toLead(leadDTO), user)
        .map(l -> new ResponseEntity<>(personConverter.toLeadIdDto(l), HttpStatus.CREATED));
  }

  @PutMapping("/person/{id}")
  public Mono<ResponseEntity<String>> updatePerson(
      @Valid @RequestBody PersonDTO leadDTO, @PathVariable("id") String id, @RequestHeader("X-User") String user) {
    LOG.debug("UPDATE FROM CONTROLLER");
    return personService
        .update(id, personConverter.toLead(leadDTO), user)
        .map(l -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/person/{id}")
  public Mono<ResponseEntity<String>> updatePersonWithPatch(
      @PathVariable("id") String id, @RequestBody Object objectPatch, @RequestHeader("X-User") String user) {
    LOG.debug("UPDATE WITH PATCH FROM CONTROLLER");
    return personService
        .updateWithPatch(id, objectPatch, user)
        .map(l -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/person/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deletePersonById(@PathVariable("id") String id, @RequestHeader("X-User") String user) {
    LOG.debug("DELETE FROM CONTROLLER");
    return personService.deleteLeadById(id, user);
  }
}
