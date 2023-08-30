package com.usman.springboot.rest.gateway;

import com.usman.springboot.domain.dtos.GetMainDto;
import com.usman.springboot.domain.request.TestRequest;
import com.usman.springboot.domain.response.TestResponse;
import com.usman.springboot.exception.WebClientApiException;
import com.usman.springboot.rest.client.WebClientApi;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
public class SampleWebClientGateway {

    @Autowired
    private WebClientApi webClientApi;
    private final String getUrl = "https://reqres.in/api/users";
    private final String postUrl = "https://reqres.in/api/register";

    public GetMainDto callGetApi() {
        try {
            log.info("callGetApi webClient call..");
            GetMainDto response = webClientApi.requestGet("TEST_API", getUrl,
                null, createHeaders(), GetMainDto.class);
            log.info("Get call response:{}", response);
            return response;
        } catch (Exception e) {
            log.error("Error found in post call:{}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    @Retryable(value = WebClientApiException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public TestResponse callPostApi(TestRequest request) {
        try {
            log.info("callPostApi webClient call..");
            TestResponse postResponse = webClientApi.requestPost("TEST_API", postUrl,
                request, createHeaders(), TestResponse.class);
            log.info("post call Response:{}", postResponse);
            return postResponse;
        } catch (Exception e) {
            log.error("Error found in post call:{}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    @Recover
    public Object callPostApiFallback(WebClientApiException e, Object obj) {
        log.info("Retry for Post call exception:{}", e.getMessage());
        return "Error Found";
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
