package com.usman.springboot.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usman.springboot.exception.BadRequestException;
import com.usman.springboot.exception.NotFoundException;
import com.usman.springboot.exception.UnAuthorizedException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

import java.util.Map;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class WebClientApi {

    @Value("${web.api.connection.timeout}")
    private Integer connectionTimeout;
    @Value("${web.api.connection.read.timeout}")
    private Integer readTimeout;
    @Value("${web.api.connection.write.timeout}")
    private Integer writeTimeout;

    public <A, B> B requestGet(String clientName, String completeUrl, @Nullable A body,
                               Map<String, String> headers, Class<B> responseType) {
        log.info("webClient Url: {}, Request Object: {}, Headers: {}",
                completeUrl, body, headers);
        try {
            WebClient webClient = getWebClient();

            HttpHeaders headersMultiValueMap = new HttpHeaders();
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                    headersMultiValueMap.add(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            B responseData = webClient.get().uri(completeUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(headersConsumer ->
                            headersConsumer.addAll(headersMultiValueMap)
                    )
                    .retrieve()
                    .bodyToMono(responseType)
                    .onErrorMap(WebClientException.class, this::handleHttpClientException)
                    .block();

            log.info("WebClient responseData:{}", responseData);
            return responseData;
        } catch (Exception e) {
            log.error("Exception found in Gateway API:" + e.getMessage());
            throw e;
        }
    }

    //@LogMethodExecutionTime
    public <A, B> B requestPost(String clientName, String completeUrl, A body,
                                Map<String, String> headers, Class<B> responseType) {
        log.info("webClient Url: {}, Request Object: {}, Headers: {}",
                completeUrl, body, headers);
        try {
            WebClient webClient = getWebClient();

            HttpHeaders headersMultiValueMap = new HttpHeaders();
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                    headersMultiValueMap.add(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            B responseData = webClient.post().uri(completeUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(headersConsumer ->
                            headersConsumer.addAll(headersMultiValueMap)
                    )
                    .bodyValue(getJsonString(body))
                    .retrieve()
                    .bodyToMono(responseType)
                    .onErrorMap(WebClientException.class, this::handleHttpClientException)
                    .block();

            log.info("WebClient responseData:{}", responseData);
            return responseData;
        } catch (Exception e) {
            log.error("Exception found in Gateway API:" + e.getMessage());
            throw e;
        }
    }

    private WebClient getWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private Throwable handleHttpClientException(Throwable ex) {
        log.info("handleHttpClientException started....");
        if (!(ex instanceof WebClientResponseException)) {
            log.error("Got an unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }
        WebClientResponseException wcre = (WebClientResponseException) ex;
        log.info("handleHttpClientException getStatusCode....{}", wcre.getStatusCode());
        switch (wcre.getStatusCode()) {
            case NOT_FOUND -> {
                log.info("handleHttpClientException NOT_FOUND....");
                throw new NotFoundException(wcre.getResponseBodyAsString());
            }
            case BAD_REQUEST -> {
                log.info("handleHttpClientException BAD_REQUEST....");
                throw new BadRequestException(wcre.getResponseBodyAsString());
            }
            case UNAUTHORIZED -> {
                log.info("handleHttpClientException UNAUTHORIZED....");
                throw new UnAuthorizedException(wcre.getResponseBodyAsString());
            }
            default -> {
                log.error("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                log.error("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
            }
        }
    }

    private String getJsonString(Object body) {
        String jsonBody = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonBody = mapper.writeValueAsString(body);
        } catch (Exception e) {
            log.error("Jackson Error:" + e.getMessage());
        }
        return jsonBody;
    }

}
