package com.usman.springboot.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 29-08-2023
 * @since : 1.0.0
 */
@RestController
@RequestMapping("/bookstore")
public class BookstoreController {

    @RequestMapping(value = "/recommended")
    public Mono<String> readingList() {
        return Mono.just(
            "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)");
    }
}
