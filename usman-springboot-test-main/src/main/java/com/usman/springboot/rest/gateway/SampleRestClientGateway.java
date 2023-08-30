package com.usman.springboot.rest.gateway;

import com.usman.springboot.domain.request.TestRequest;
import com.usman.springboot.domain.response.TestResponse;
import com.usman.springboot.exception.WebClientApiException;
import com.usman.springboot.rest.client.RestClientApi;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class SampleRestClientGateway {

    public static final String TEST_API = "TEST_API";
    @Autowired
    private RestClientApi restClientApi;
    private final String getUrl = "https://reqres.in/api/users";
    private final String postUrl = "https://reqres.in/api/register";

    public Object callGetApi() {
        try {
            log.info("callGetApi restClient call..");
            return restClientApi.request(TEST_API, HttpMethod.GET,
                getUrl, null, createHeaders(), Object.class);
        } catch (Exception e) {
            log.error("Error found in post call:{}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public TestResponse callPostApi(TestRequest request) {

        try {
            log.info("callPostApi restClient call..");
            return restClientApi.request(TEST_API, HttpMethod.POST,
                postUrl, request, createHeaders(), TestResponse.class);
        } catch (Exception e) {
            log.error("Error found in post call: {}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    @Recover
    public Object callPostApiFallback(WebClientApiException e, Object obj) {
        log.info("Retry for Post call exception: {}", e.getMessage());
        return "Error Found";
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
