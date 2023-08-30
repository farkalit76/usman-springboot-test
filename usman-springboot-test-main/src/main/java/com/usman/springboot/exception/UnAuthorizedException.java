package com.usman.springboot.exception;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String error){
        super(error);
    }
}
