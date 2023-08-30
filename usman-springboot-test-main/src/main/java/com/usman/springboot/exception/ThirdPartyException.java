package com.usman.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
public class ThirdPartyException extends RuntimeException {

    private final String code;
    private final String message;
    private final String vendor;
    private final Throwable throwable;
}


