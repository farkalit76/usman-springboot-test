package com.usman.springboot.rest.client;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
public interface RestProvider {
    /**
     * Get the set name of partner(API provider while invoking)
     *
     * @return {@link String}
     */
    String getName();
}
