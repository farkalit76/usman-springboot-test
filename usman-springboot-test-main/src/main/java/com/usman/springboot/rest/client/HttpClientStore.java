package com.usman.springboot.rest.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class HttpClientStore {


//    public RestTemplate getRestTemplate() {
//        log.info("**** New rest template is creating ****");
//        return new RestTemplate();
//    }
//    public RestTemplate getRestTemplateForClient(String clientName) {
//        HttpClientProperties clientProperties = cache.get(clientName);
//        if (clientProperties != null) {
//            return clientProperties.getRestTemplate();
//        }
//        throw new HttpClientNotRegisteredException(clientName);
//    }

    @Bean(name="pooledClient")
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        log.info("**** New rest template is creating ****");
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        return clientHttpRequestFactory;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("RestClient-PoolScheduler");
        scheduler.setPoolSize(50);
        return scheduler;
}
}
