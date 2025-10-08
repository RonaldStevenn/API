package com.tecnoshop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(String message) {
        super(message);
    }
}
