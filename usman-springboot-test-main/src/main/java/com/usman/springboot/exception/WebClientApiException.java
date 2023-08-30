package com.usman.springboot.exception;

import lombok.*;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class WebClientApiException extends RuntimeException{

    private int code;
    private String errorCode;
    private String errorMessage;
    public WebClientApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public WebClientApiException(int code, String errorCode, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public WebClientApiException(Throwable cause, String errorCode, String errorMessage) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
