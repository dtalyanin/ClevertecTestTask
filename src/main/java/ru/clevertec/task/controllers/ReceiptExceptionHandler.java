package ru.clevertec.task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.task.exceptions.FileWritingException;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.exceptions.OrderParamException;
import ru.clevertec.task.models.ErrorResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@ControllerAdvice
public class ReceiptExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> findValidationExceptionInParameters(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().iterator().next();
        ErrorResponse errorResponse = new ErrorResponse(constraintViolation.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderParamException.class)
    public ResponseEntity<ErrorResponse> findWrongParameters(OrderParamException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getWrongValue(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> cannotFoundItem(OrderItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMissingId(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileWritingException.class)
    public ResponseEntity<ErrorResponse> cannotWriteToFile(FileWritingException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> findProblemWithDatabase(SQLException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> findValidationExceptionInParameters(MethodArgumentNotValidException e) {
        FieldError error = e.getFieldError();
        String message = String.format("%s for field '%s' and value '%s'", error.getDefaultMessage(),
                error.getField(), error.getRejectedValue());
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
