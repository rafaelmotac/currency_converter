package com.rmc.currency_converter.web.rest;

import com.rmc.currency_converter.exception.CurrencyConverterException;
import com.rmc.currency_converter.exception.InvalidCurrencyException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    public static final String ERROR_PROCESSING_URL = "https://com.rmc.currency_converter/error-processing";

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create(ERROR_PROCESSING_URL));
        problemDetail.setTitle("Constraint violation");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(CurrencyConverterException.class)
    ProblemDetail handleCurrencyConverterException(CurrencyConverterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create(ERROR_PROCESSING_URL));
        problemDetail.setTitle("Internal error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    ProblemDetail handleInvalidCurrencyException(InvalidCurrencyException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create(ERROR_PROCESSING_URL));
        problemDetail.setTitle("Invalid currency");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    ProblemDetail handleRuntimeException(Throwable ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create(ERROR_PROCESSING_URL));
        problemDetail.setTitle("Internal error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

}
