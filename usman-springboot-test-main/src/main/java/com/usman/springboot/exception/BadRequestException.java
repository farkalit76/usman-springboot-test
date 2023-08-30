package com.usman.springboot.exception;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String error){
        super(error);
    }
}
