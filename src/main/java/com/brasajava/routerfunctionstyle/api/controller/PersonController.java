package com.brasajava.routerfunctionstyle.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.rest.webmvc.json.patch.JsonPatchPatchConverter;
import org.springframework.data.rest.webmvc.json.patch.Patch;
import org.springframework.data.rest.webmvc.json.patch.PatchException;
import org.springframework.expression.spel.SpelEvaluationException;
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
import com.brasajava.routerfunctionstyle.exception.PersonPatchNotFoundFieldException;
import com.brasajava.routerfunctionstyle.exception.PersonPatchNotFoundKeyOperationException;
import com.brasajava.routerfunctionstyle.service.PersonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller/person")
public class PersonController {
  private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);
  private PersonService service;
  private PersonConverter converter;
  private ObjectMapper objectMapper;

  public PersonController(
      PersonService personService, PersonConverter personConverter, ObjectMapper objectMapper) {
    this.service = personService;
    this.converter = personConverter;
    this.objectMapper = objectMapper;
  }

  @GetMapping("/hello")
  public Mono<String> hello() {
    LOG.info("HELLO FROM CONTROLLER");
    return Mono.just("Hello World from PersonController");
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<PersonDTO> getAllLead() {
    LOG.debug("FIND ALL FROM CONTROLLER");
    return service.findAll().map(converter::toPersonDto);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<PersonDTO>> getLeadById(@PathVariable("id") String id) {
    LOG.debug("FIND BY ID FROM CONTROLLER");
    return service
        .findById(id)
        .map(l -> ResponseEntity.ok(converter.toPersonDto(l)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<PersonIDDTO>> createPerson(
      @Valid @RequestBody PersonDTO leadDTO, @RequestHeader("X-User") String user) {
    LOG.debug("CREATE FROM CONTROLLER");
    return service
        .create(converter.toPerson(leadDTO), user)
        .map(l -> new ResponseEntity<>(converter.toPersonIdDto(l), HttpStatus.CREATED));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<String>> updatePerson(
      @Valid @RequestBody PersonDTO leadDTO,
      @PathVariable("id") String id,
      @RequestHeader("X-User") String user) {
    LOG.debug("UPDATE FROM CONTROLLER");
    return service
        .update(id, converter.toPerson(leadDTO), user)
        .map(l -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/{id}")
  public Mono<ResponseEntity<String>> updatePersonWithPatch(
      @PathVariable("id") String id,
      @RequestBody Object objectPatch,
      @RequestHeader("X-User") String user) {
    LOG.debug("UPDATE WITH PATCH FROM CONTROLLER");
    return service
        .findById(id)
        .map(converter::toPersonDto)
        .map(dto -> applyPatch(dto, objectPatch))
        .flatMap(dto -> service.update(id, converter.toPerson(dto), user))
        .map(p -> new ResponseEntity<String>(HttpStatus.NO_CONTENT))
        .defaultIfEmpty(new ResponseEntity<String>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deletePersonById(
      @PathVariable("id") String id, @RequestHeader("X-User") String user) {
    LOG.debug("DELETE FROM CONTROLLER");
    return service.deleteById(id, user);
  }

  private PersonDTO applyPatch(PersonDTO personDTO, Object objectPatch) {
    Patch patch = convertRequestToPatch(objectPatch);
    try {
      patch.apply(personDTO, PersonDTO.class);
    } catch (PropertyReferenceException | PatchException | SpelEvaluationException ex) {
      throw new PersonPatchNotFoundFieldException();
    }
    return personDTO;
  }

  private Patch convertRequestToPatch(Object objectPatch) {
    JsonNode node = objectMapper.convertValue(objectPatch, JsonNode.class);
    Patch patch;
    try {
      patch = new JsonPatchPatchConverter(objectMapper).convert(node);
    } catch (NullPointerException ex) {
      throw new PersonPatchNotFoundKeyOperationException();
    }
    return patch;
  }
}
