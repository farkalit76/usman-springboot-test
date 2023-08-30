package com.usman.springboot.rest.gateway;

import com.usman.springboot.annotation.LogMethodExecutionTime;
import com.usman.springboot.domain.dtos.GetMainDto;
import com.usman.springboot.domain.request.TestRequest;
import com.usman.springboot.domain.response.TestResponse;
import com.usman.springboot.exception.WebClientApiException;
import com.usman.springboot.rest.client.RestClientBuilder;
import com.usman.springboot.rest.client.RestClientBuilder.Get;
import com.usman.springboot.rest.client.RestClientBuilder.ParameterizedPost;
import com.usman.springboot.rest.client.SingletonWebClientFactory;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class SampleRestClientBuilderGateway {

    @Autowired
    private RestClientBuilder restClient;

    private final String getUrl = "https://reqres.in/api/users";
    private final String postUrl = "https://reqres.in/api/register";
    private static final String TEST_API = "TEST_API";

    @LogMethodExecutionTime
    public GetMainDto callGetApi() {
        log.info("callGetApi gateway call..");
        try {
            Get<GetMainDto, Object> getResponse = Get.<GetMainDto, Object>builder()
                .webClient(SingletonWebClientFactory.webClient())
                .responseType(GetMainDto.class).url(getUrl).headers(createHeaders())
                .apiProvider(() -> TEST_API).uriVariables(new Object[]{})
                .build();
            return getGetApiResponse(getResponse);
        } catch (Exception e) {
            log.error("Error found in getAccountDetails:{}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    private <T> T getGetApiResponse(Get<T, Object> callRequest) {
        T body = restClient.get(callRequest).block();
        if (body == null) {
            log.info("Response is null");
            return null;
        }
        return body;
    }

    public TestResponse callPostApi(TestRequest request) {
        log.info("callPostApi gateway call..");
        try {
            ParameterizedTypeReference<TestResponse> type = new ParameterizedTypeReference<>() {
            };
            ParameterizedPost<TestResponse, TestRequest> response = ParameterizedPost.<TestResponse, TestRequest>builder()
                .webClient(SingletonWebClientFactory.webClient())
                .responseType(type).requestType(TestRequest.class).request(request)
                .url(postUrl).headers(createHeaders())
                .apiProvider(() -> TEST_API).uriVariables(new Object[]{})
                .build();
            return getPostApiResponse(response);
        } catch (Exception e) {
            log.error("Exception while fetching post call:{}", e);
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }

    private <T> T getPostApiResponse(ParameterizedPost<T, TestRequest> callRequest) {
        T body = restClient.post(callRequest).block();
        if (body == null) {
            log.info("Response is null");
            return null;
        }
        return body;
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
