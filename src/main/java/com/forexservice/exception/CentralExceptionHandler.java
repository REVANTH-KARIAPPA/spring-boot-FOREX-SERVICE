package com.forexservice.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(UserNameException.class)
    public ResponseEntity<String> exceptionHandler(UserNameException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>methordArgumentNotValdException(MethodArgumentNotValidException e){
        String message=e.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.joining(","));
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<String> exceptionHandler(CurrencyNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }








}