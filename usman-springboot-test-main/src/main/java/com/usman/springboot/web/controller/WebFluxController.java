package com.usman.springboot.web.controller;

import com.usman.springboot.webflux.dto.Customer;
import com.usman.springboot.webflux.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-08-2023
 * @since : 1.0.0
 */
@RestController
@RequestMapping("/customer")
public class WebFluxController {

    @Autowired
    private CustomerService customerService;

    @GetMapping( value = "/all")
    public List<Customer> getAllCustomer()
    {
        return customerService.getCustomers();
    }

    @GetMapping( value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getAllCustomerStream()
    {
        return customerService.getCustomersStream();
    }
}
