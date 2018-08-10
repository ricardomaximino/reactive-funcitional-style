package com.brasajava.routerfunctionstyle;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class RouterFunctionStyleApplicationTests {

  Flux<String> myFlux;

  @Before
  public void before() {
    myFlux =
        Flux.fromIterable(Arrays.asList("Ricardo", "Maximino", "Gonçalves", "de", "Moraes"))
            .map(
                name -> {
                  if (name.equals("de")) {
                    throw Exceptions.propagate(new RuntimeException("My Exception"));
                  }
                  return name.toUpperCase();
                });
  }

  @Test
  public void contextLoads() {

    myFlux.subscribe(
        s -> System.out.println("Success => " + s),
        e -> System.out.println("Error"),
        () -> System.out.println("Complete"));

    StepVerifier.create(myFlux)
        .expectNext("RICARDO")
        .expectNext("MAXIMINO")
        .expectNext("GONÇALVES")
        //        .expectErrorMessage("My Exception")
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  public void cathError() {
    Flux<String> catchFlux =
        Flux.create(
            emitter -> {
              try {
                myFlux.subscribe(
                    success -> System.out.println("Success"),
                    error -> System.out.println("Error"),
                    () -> System.out.println("Complete"));
              } catch (Exception ex) {
                System.out.println("Catched Exception ...");
                ex.printStackTrace();
              }
            });
  }
}
