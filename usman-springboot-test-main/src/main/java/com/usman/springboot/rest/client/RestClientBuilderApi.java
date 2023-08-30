package com.usman.springboot.rest.client;

import com.usman.springboot.exception.ThirdPartyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class RestClientBuilderApi implements RestClientBuilder {

    private static final String THIRD_PARTY_EXP_MSG = "Third Party integration exception.";
    private static final String URL_CANNOT_BE_NULL_EMPTY = "URL cannot be null/empty.";
    private static final String URL_AND_OR_REQUEST_CANNOT_BE_NULL_EMPTY = "URL and/or request cannot be null/empty.";
    private static final String REQUEST_HEADERS_CANNOT_BE_NULL = "Request Headers cannot be null.";
    private static final ThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("RestClient_Thread_Factory@");
    private static final Executor EXECUTOR = Executors.newCachedThreadPool(THREAD_FACTORY);


    @Override
    public <T, R> Mono<T> get(Get<T, R> get) throws RestClientException {
        if ((Objects.isNull(get.getUrl()) )) {
            log.error(URL_CANNOT_BE_NULL_EMPTY);
            return Mono.empty();
        }
        if (get.getHeaders() == null) {
            log.error(REQUEST_HEADERS_CANNOT_BE_NULL);
            return Mono.empty();
        }
        return get.getWebClient()
                .get()
                .uri(get.getUrl())
                .headers(httpHeaders -> get.getHeaders().forEach(httpHeaders::add))
                .retrieve()
                .bodyToMono(get.getResponseType())
                .timeout(Duration.ofSeconds(60))
                .doOnSuccess(t -> doOnSuccess(t, get.getUrl(), get.getApiProvider()))
                .doOnError(throwable -> doOnException(throwable, get.getUrl(), get.getApiProvider()))
                .subscribeOn(Schedulers.fromExecutor(EXECUTOR));
    }

    @Override
    public <T, R> Mono<T> post(Post<T, R> post) throws RestClientException {
        if (Objects.isNull(post.getUrl()) || post.getRequest() == null) {
            log.error(URL_AND_OR_REQUEST_CANNOT_BE_NULL_EMPTY);
            return Mono.empty();
        }
        if (post.getHeaders() == null) {
            log.error(REQUEST_HEADERS_CANNOT_BE_NULL);
            return Mono.empty();
        }
        log.info("Inside post with request::::{}", post.getRequest());
        return post.getWebClient()
                .post()
                .uri(post.getUrl(), post.getUriVariables())
                .headers(httpHeaders -> post.getHeaders().forEach(httpHeaders::add))
                .body(Mono.just(post.getRequest()), post.getRequestType())
                .retrieve()
                .bodyToMono(post.getResponseType())
                .timeout(Duration.ofSeconds(60))
                .doOnSuccess(t -> doOnSuccess(t, post.getUrl(), post.getApiProvider()))
                .doOnError(throwable -> doOnException(throwable, post.getUrl(), post.getApiProvider()))
                .subscribeOn(Schedulers.fromExecutor(EXECUTOR));
    }

    @Override
    public <T, R> Mono<T> post(ParameterizedPost<T, R> post) throws RestClientException {
        if (Objects.isNull(post.getUrl()) || post.getRequest() == null) {
            log.error(URL_AND_OR_REQUEST_CANNOT_BE_NULL_EMPTY);
            return Mono.empty();
        }
        if (post.getHeaders() == null) {
            log.error(REQUEST_HEADERS_CANNOT_BE_NULL);
            return Mono.empty();
        }
        log.info("Inside post with request::::{}", post.getRequest());
        return post.getWebClient()
                .post()
                .uri(post.getUrl(), post.getUriVariables())
                .headers(httpHeaders -> post.getHeaders().forEach(httpHeaders::add))
                .body(Mono.just(post.getRequest()), post.getRequestType())
                .retrieve()
                .bodyToMono(post.getResponseType())
                .timeout(Duration.ofSeconds(60))
                .doOnSuccess(t -> doOnSuccess(t, post.getUrl(), post.getApiProvider()))
                .doOnError(throwable -> doOnException(throwable, post.getUrl(), post.getApiProvider()))
                .subscribeOn(Schedulers.fromExecutor(EXECUTOR));
    }


    private static <T> void doOnSuccess(T response, String url, RestProvider apiProvider) {
        log.info("Request successful for URL:{}, apiProvider:{}", url, apiProvider.getName());
        log.info("Request successful with response:{}", response);
        Mono.justOrEmpty(response);
    }

    private static void doOnException(Throwable throwable, String url, RestProvider apiProvider) {
        log.error("Exception for request to url:{}, apiProvider:{}", url, apiProvider.getName());
        log.error("Exception occurred throwable stack trace:---->>", throwable.getStackTrace());
        if (!(throwable instanceof WebClientResponseException webClientResponseException)) {
            throw new ThirdPartyException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()),
                    THIRD_PARTY_EXP_MSG, apiProvider.getName(), throwable);
        }
        throw new ThirdPartyException(String.valueOf(webClientResponseException.getStatusCode()),
                webClientResponseException.getResponseBodyAsString(),
                apiProvider.getName(), throwable);
    }
}
