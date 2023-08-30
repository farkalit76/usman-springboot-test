package com.usman.springboot.rest.client;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
public interface RestClientBuilder {

    /**
     * Use this method to call get type application
     *
     * @param get
     * @param <T>
     * @param <R>
     * @return
     * @throws RestClientException
     */
    <T, R> Mono<T> get(Get<T, R> get) throws RestClientException;

    /**
     * Use this method when you don't need parameterized response.
     *
     * @param post {@link Post}
     * @param <T>  {@link T response type}
     * @param <R>  {@link R request type}
     * @return {@link Mono}
     * @throws RestClientException
     */
    <T, R> Mono<T> post(Post<T, R> post) throws RestClientException;

    /**
     * Use this method when you need parameterized response.
     *
     * @param post {@link ParameterizedPost}
     * @param <T>  {@link T response type}
     * @param <R>  {@link R request type}
     * @return {@link Mono}
     * @throws RestClientException
     */
    <T, R> Mono<T> post(ParameterizedPost<T, R> post) throws RestClientException;

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class Get<T, R> {
        private final String url;
        private final WebClient webClient;
        private final Map<String, String> headers;
        private final Class<T> responseType;
        private final RestProvider apiProvider;
        private final Object[] uriVariables;
    }

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class Post<T, R> {
        private final String url;
        private final WebClient webClient;
        private final R request;
        private final Map<String, String> headers;
        private final Class<R> requestType;
        private final Class<T> responseType;
        private final RestProvider apiProvider;
        private final Object[] uriVariables;
    }

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class ParameterizedPost<T, R> {
        private final String url;
        private final WebClient webClient;
        private final R request;
        private final Map<String, String> headers;
        private final Class<R> requestType;
        private final ParameterizedTypeReference<T> responseType;
        private final RestProvider apiProvider;
        private final Object[] uriVariables;
    }

}
