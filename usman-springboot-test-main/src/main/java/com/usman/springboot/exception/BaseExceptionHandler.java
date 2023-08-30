package com.usman.springboot.exception;

import com.usman.springboot.domain.common.ApiResponse;
import com.usman.springboot.domain.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@RestControllerAdvice
@ResponseBody
public class BaseExceptionHandler {

    public static final String REQUEST_ARGUMENT_NOT_VALID_EXCEPTION_CODE = "INDIE504";

    public static final String REQUEST_NOT_VALID_EXCEPTION_CODE = "INDIE505";

    public static final String REQUEST_ARGUMENT_NOT_VALID_EXCEPTION_MSG = "Request arguments are invalid";
    public static final String REQUEST_NOT_VALID_EXCEPTION_MSG = "Request is invalid or JSON parse error";

    @ResponseStatus(value = BAD_GATEWAY)
    @ExceptionHandler(value = {DataAccessResourceFailureException.class})
    public ApiResponse<ErrorResponse> dataAccessResourceFailureException(
            DataAccessResourceFailureException ex) {
        log.error("DataAccessResourceFailureException occurred:{}", ex.getMessage());
        return createBaseResponse(ex.getMessage());
    }

    @ResponseStatus(value = BAD_GATEWAY)
    @ExceptionHandler(value = {JDBCConnectionException.class})
    public ApiResponse<ErrorResponse> jdbcConnectionException(JDBCConnectionException ex) {
        log.error("JDBCConnectionException occurred:{}", ex.getMessage());
        return createBaseResponse(ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<ErrorResponse> handleBindException(BindException exception) {
        log.error("BindException occurred {}", exception.getMessage());
        return createBaseResponse(exception.getMessage());
    }


    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.class)
    public ApiResponse<ErrorResponse> handleInvalidInputDataException(
            HttpClientErrorException exception) {
        log.error("HttpClientErrorException occurred {}:", exception.getMessage());
        return createBaseResponse(" Unauthorized key - " + exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception) {
        log.error("ConstraintViolationException occurred {}", exception.getMessage());
        return createBaseResponse(exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ApiResponse<ErrorResponse> handleMethodArgumentNotValidError(MethodArgumentNotValidException ex) {
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(REQUEST_ARGUMENT_NOT_VALID_EXCEPTION_CODE,
                REQUEST_ARGUMENT_NOT_VALID_EXCEPTION_MSG, errors);
        return ApiResponse.error(errorResponse);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ApiResponse<ErrorResponse> handleHttpMessageNotReadableError(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(REQUEST_NOT_VALID_EXCEPTION_CODE, REQUEST_NOT_VALID_EXCEPTION_MSG);
        return ApiResponse.error(errorResponse);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WebClientApiException.class)
    protected ApiResponse<ErrorResponse> handleWebClientApiError(WebClientApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return ApiResponse.error(errorResponse);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorResponse> handleUncaughtException(Exception exception) {

        log.error("Internal Server Error: {}", exception);
        if (exception instanceof DataIntegrityViolationException) {
            Throwable rootCause = ((DataIntegrityViolationException) exception).getRootCause();
            if (rootCause != null && rootCause.getLocalizedMessage().toUpperCase()
                    .contains("DUPLICATE")) {
                return createBaseResponse("DUPLICATE_REQUEST");
            }
        }
        return createBaseResponse("INTERNAL_SERVER_ERROR");
    }

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    public ApiResponse<ErrorResponse> internalServerError(HttpServerErrorException.InternalServerError ex) {
        log.error("InternalServerError occurred:{}", ex.getMessage());
        return createBaseResponse(ex.getMessage());
    }


    private ApiResponse<ErrorResponse> createBaseResponse(String msg) {
        ErrorResponse response = new ErrorResponse(504, "Generic_Error", msg, null);
        return ApiResponse.error(response);
    }
}
