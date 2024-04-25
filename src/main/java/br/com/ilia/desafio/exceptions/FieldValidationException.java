package br.com.ilia.desafio.exceptions;

public class FieldValidationException extends RuntimeException {

    public FieldValidationException(String message) {
        super(message);
    }

    public FieldValidationException() {

    }
}
