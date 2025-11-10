package com.tecnoshop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador de excepciones global para toda la aplicación.
 * Captura excepciones específicas y las formatea en una respuesta JSON consistente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación de argumentos de método (DTOs).
     * Se activa cuando una validación anotada con @Valid en un controlador falla.
     *
     * @param ex La excepción MethodArgumentNotValidException lanzada por Spring.
     * @return Un ResponseEntity con estado 400 (Bad Request) y un cuerpo JSON
     *         que detalla los campos que fallaron la validación y sus respectivos mensajes de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Error de Validación");
        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
