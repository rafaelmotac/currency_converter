package com.rmc.currency_converter.web.rest;

import com.rmc.currency_converter.exception.CurrencyConverterException;
import com.rmc.currency_converter.exception.InvalidCurrencyException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ExceptionTranslatorTest {

    @Mock
    private ConstraintViolationException constraintViolationException;

    @Mock
    private CurrencyConverterException currencyConverterException;

    @Mock
    private InvalidCurrencyException invalidCurrencyException;

    @Mock
    private RuntimeException runtimeException;

    @InjectMocks
    private ExceptionTranslator exceptionTranslator;


    @Test
    void shouldHandleConstraintViolationException() {
        when(constraintViolationException.getMessage()).thenReturn("Constraint violation");

        ProblemDetail response = exceptionTranslator.handleConstraintViolationException(constraintViolationException);

        assertEquals("Constraint violation", response.getDetail());
    }

    @Test
    void shouldHandleCurrencyConverterException() {
        when(currencyConverterException.getMessage()).thenReturn("Internal error");

        ProblemDetail response = exceptionTranslator.handleCurrencyConverterException(currencyConverterException);

        assertEquals("Internal error", response.getDetail());
    }

    @Test
    void shouldHandleInvalidCurrencyException() {
        when(invalidCurrencyException.getMessage()).thenReturn("Invalid currency");

        ProblemDetail response = exceptionTranslator.handleInvalidCurrencyException(invalidCurrencyException);

        assertEquals("Invalid currency", response.getDetail());
    }

    @Test
    void shouldHandleRuntimeException() {
        when(runtimeException.getMessage()).thenReturn("Internal error");

        ProblemDetail response = exceptionTranslator.handleRuntimeException(runtimeException);

        assertEquals("Internal error", response.getDetail());
    }
}