package com.brasajava.routerfunctionstyle.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;

import com.brasajava.routerfunctionstyle.api.dto.ErrorMessageDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends WebFluxResponseStatusExceptionHandler {

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleException(RuntimeException throwable) {

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ErrorMessageDTO body =
        new ErrorMessageDTO(
            LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            throwable.getClass().getName(),
            throwable.getMessage());

    return new ResponseEntity<>(body, httpStatus);
  }
}
