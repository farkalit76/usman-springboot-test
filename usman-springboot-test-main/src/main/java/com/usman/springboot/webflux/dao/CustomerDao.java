package com.usman.springboot.webflux.dao;

import com.usman.springboot.webflux.dto.Customer;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-08-2023
 * @since : 1.0.0
 */
@Component
public class CustomerDao {

    private static void sleepFor(int time) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Customer> getAllCustomer() {
        return IntStream.range(1, 50)
            .peek(CustomerDao::sleepFor)
            .peek(i -> System.out.println("processing count :" + i))
            .mapToObj(i -> new Customer(i, "Customer-" + i))
            .collect(Collectors.toList());
    }

    public Flux<Customer> getAllCustomerStream() {
        return Flux.range(1, 50)
            .delayElements(Duration.ofMillis(100))
            .doOnNext(i -> System.out.println("stream processing count :" + i))
            .map(i -> new Customer(i, "St-Customer-" + i));
    }
}
