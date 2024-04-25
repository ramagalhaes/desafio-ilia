package br.com.ilia.desafio.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {DateException.class, FieldValidationException.class, ExpedienteValidationException.class})
    public ResponseEntity<StandardError> handleBadRequestExceptions(RuntimeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new StandardError(e.getMessage()));
    }

    @ExceptionHandler(BatidaExistsException.class)
    public ResponseEntity<StandardError> batidaExistsException(BatidaExistsException e) {
        return ResponseEntity.status(CONFLICT).body(new StandardError(e.getMessage()));
    }
}
