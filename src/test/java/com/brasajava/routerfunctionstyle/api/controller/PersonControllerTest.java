package com.brasajava.routerfunctionstyle.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.brasajava.routerfunctionstyle.api.converter.PersonConverter;
import com.brasajava.routerfunctionstyle.api.dto.PersonDTO;
import com.brasajava.routerfunctionstyle.api.dto.PersonIDDTO;
import com.brasajava.routerfunctionstyle.domain.entity.Person;
import com.brasajava.routerfunctionstyle.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class PersonControllerTest {
  @MockBean private PersonService service;
  @MockBean private PersonConverter converter;
  @Autowired private WebTestClient webClient;
  @Autowired private ObjectMapper objectMapper;
  private final String baseUrl = "/controller/person/";
  private final String id = "123456789";
  private final String user = "1234";
  private static final String X_USER_HEADER = "X-User";

  @Test
  public void testHello() {
    webClient
        .get()
        .uri(baseUrl + "/hello")
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .consumeWith(
            response -> {
              assertThat(response.getResponseBody()).isEqualTo("Hello World from PersonController");
              System.out.println(response);
            });
  }

  @Test
  public void testGetAll() {
    when(service.findAll()).thenReturn(Flux.just(createPerson(), createPerson()));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());

    webClient
        .get()
        .uri(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Person.class)
        .hasSize(2)
        .consumeWith(System.out::println);

    verify(service, times(1)).findAll();
    verify(converter, times(2)).toPersonDto(any());
  }

  @Test
  public void testGetById() {
    when(service.findById(anyString())).thenReturn(Mono.just(createPerson()));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());

    webClient
        .get()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(PersonDTO.class)
        .consumeWith(System.out::println);

    verify(service, times(1)).findById(anyString());
    verify(converter, times(1)).toPersonDto(any());
  }

  @Test
  public void testCreate() {
    when(service.create(any(), anyString())).thenReturn(Mono.just(createPerson()));
    when(converter.toPerson(any())).thenReturn(createPerson());
    when(converter.toPersonIdDto(any())).thenReturn(createPersonIdDto());

    webClient
        .post()
        .uri(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(BodyInserters.fromObject(createPersonDto()))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(PersonDTO.class)
        .consumeWith(System.out::println);

    verify(service, times(1)).create(any(), anyString());
    verify(converter, times(1)).toPerson(any());
    verify(converter, times(1)).toPersonIdDto(any());
  }

  @Test
  public void testUpdate() {
    when(service.update(anyString(), any(), anyString()))
        .thenReturn(Mono.just(new Person("123456789", "name", "lastname", "username", "password")));
    when(converter.toPerson(any())).thenReturn(createPerson());

    webClient
        .put()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(BodyInserters.fromObject(createPersonDto()))
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).update(anyString(), any(), anyString());
    verify(converter, times(1)).toPerson(any());
  }

  @Test
  public void testUpdateWithPatch() {
    when(service.findById(anyString())).thenReturn(Mono.just(createPerson()));
    when(service.update(anyString(), any(), anyString()))
        .thenReturn(Mono.just(new Person("123456789", "name", "lastname", "username", "password")));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());
    when(converter.toPerson(any())).thenReturn(createPerson());

    webClient
        .patch()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(BodyInserters.fromObject(createPathObject()))
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).findById(anyString());
    verify(converter, times(1)).toPersonDto(any());
    verify(converter, times(1)).toPerson(any());
    verify(service, times(1)).update(anyString(), any(), anyString());
  }

  @Test
  public void testUpdateWithPatch_with_unsupported_field() {
    when(service.findById(anyString())).thenReturn(Mono.just(createPerson()));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());
    assertNotNull(objectMapper);
    webClient
        .patch()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(BodyInserters.fromObject(createPathObject_with_unsupported_field()))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).findById(anyString());
    verify(converter, times(1)).toPersonDto(any());
  }

  @Test
  public void testUpdateWithPatch_with_empty_keys() {
    when(service.findById(anyString())).thenReturn(Mono.just(createPerson()));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());
    assertNotNull(objectMapper);
    webClient
        .patch()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(BodyInserters.fromObject(createPathObject_with_empty_keys()))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).findById(anyString());
    verify(converter, times(1)).toPersonDto(any());
  }

  @Test
  public void testUpdateWithPatch_with_object_property_reference_not_correctly_spell() {
    when(service.findById(anyString())).thenReturn(Mono.just(createPerson()));
    when(converter.toPersonDto(any())).thenReturn(createPersonDto());
    assertNotNull(objectMapper);
    webClient
        .patch()
        .uri(baseUrl + id)
        .accept(MediaType.APPLICATION_JSON)
        .header(X_USER_HEADER, user)
        .body(
            BodyInserters.fromObject(
                createPathObject_with_object_property_reference_not_correctly_spell()))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).findById(anyString());
    verify(converter, times(1)).toPersonDto(any());
  }

  @Test
  public void testDeleteById() {
    when(service.deleteById(anyString(), anyString())).thenReturn(Mono.empty());

    webClient
        .delete()
        .uri(baseUrl + id)
        .header(X_USER_HEADER, user)
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .consumeWith(System.out::println);

    verify(service, times(1)).deleteById(anyString(), anyString());
  }

  private Person createPerson() {
    return new Person("123456789", "name", "lastname", "username", "password");
  }

  private PersonDTO createPersonDto() {
    return new PersonDTO("123456789", "name", "lastname", "username", "password");
  }

  private PersonIDDTO createPersonIdDto() {
    return new PersonIDDTO(id);
  }

  private List<LinkedHashMap<String, String>> createPathObject() {
    List<LinkedHashMap<String, String>> objectPatch = new ArrayList<>();

    LinkedHashMap<String, String> name = new LinkedHashMap<>();
    name.put("op", "replace");
    name.put("path", "/name");
    name.put("value", "Ricardo");
    objectPatch.add(name);

    LinkedHashMap<String, String> lastname = new LinkedHashMap<>();
    lastname.put("op", "replace");
    lastname.put("path", "/lastname");
    lastname.put("value", "Maximino");
    objectPatch.add(lastname);

    LinkedHashMap<String, String> username = new LinkedHashMap<>();
    username.put("op", "replace");
    username.put("path", "/username");
    username.put("value", "ricardomaximino");
    objectPatch.add(username);

    LinkedHashMap<String, String> password = new LinkedHashMap<>();
    password.put("op", "replace");
    password.put("path", "/password");
    password.put("value", "secrete");
    objectPatch.add(password);

    return objectPatch;
  }

  private List<LinkedHashMap<String, String>> createPathObject_with_unsupported_field() {
    List<LinkedHashMap<String, String>> objectPatch = new ArrayList<>();

    LinkedHashMap<String, String> name = new LinkedHashMap<>();
    name.put("op", "replace");
    name.put("path", "/named");
    name.put("value", "1");
    objectPatch.add(name);

    return objectPatch;
  }

  private List<LinkedHashMap<String, String>> createPathObject_with_empty_keys() {
    List<LinkedHashMap<String, String>> objectPatch = new ArrayList<>();

    LinkedHashMap<String, String> name = new LinkedHashMap<>();
    name.put("path", "/name");
    name.put("value", "1");
    objectPatch.add(name);

    return objectPatch;
  }

  private List<LinkedHashMap<String, String>>
      createPathObject_with_object_property_reference_not_correctly_spell() {
    List<LinkedHashMap<String, String>> objectPatch = new ArrayList<>();

    LinkedHashMap<String, String> name = new LinkedHashMap<>();
    name.put("op", "replace");
    name.put("path", "/nameId");
    name.put("value", "1");
    objectPatch.add(name);

    return objectPatch;
  }
}

@Configuration
class TestConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
