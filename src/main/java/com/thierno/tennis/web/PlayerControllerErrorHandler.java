package com.thierno.tennis.web;

import com.thierno.tennis.Error;
import com.thierno.tennis.service.PlayerAlreadyExistsException;
import com.thierno.tennis.service.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PlayerControllerErrorHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handlePlayerAlreadyExistsException(PlayerAlreadyExistsException ex) {
        return new Error(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){

        var errors = new HashMap<String, String >();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fielname = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fielname, errorMessage);
        });
        return errors;
    }
}
