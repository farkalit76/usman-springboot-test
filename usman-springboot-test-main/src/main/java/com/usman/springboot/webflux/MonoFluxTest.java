package com.usman.springboot.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-08-2023
 * @since : 1.0.0
 */
public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<?> monoString = Mono.just("usmanString")
            .then(Mono.error(new Exception("Found Mono error"))).log();
        monoString.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
        System.out.println("all done!");
    }

    @Test
    public void testFlux() {
        final Flux<String> stringFlux = Flux.just("Java", "Springboot", "Hibernate")
            .concatWithValues("Kafka")
            .concatWith(Flux.error(new RuntimeException("Found Flux error")))
            .concatWithValues("Redis Cahce")
            .log();
        stringFlux.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }
}
