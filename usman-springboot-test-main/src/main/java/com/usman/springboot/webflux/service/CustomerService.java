package com.usman.springboot.webflux.service;

import com.usman.springboot.webflux.dao.CustomerDao;
import com.usman.springboot.webflux.dto.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-08-2023
 * @since : 1.0.0
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getCustomers() {
        long start = System.currentTimeMillis();
        final List<Customer> customers = customerDao.getAllCustomer();
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed :{}" + (end-start));
        return customers;
    }

    public Flux<Customer> getCustomersStream() {
        long start = System.currentTimeMillis();
        final Flux<Customer> customers = customerDao.getAllCustomerStream();
        long end = System.currentTimeMillis();
        System.out.println("Stream Time elapsed :{}" + (end-start));
        return customers;
    }
}
