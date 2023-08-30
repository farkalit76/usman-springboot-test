package com.usman.springboot.rest.gateway;

import com.usman.springboot.domain.request.AFlyerRequest;
import com.usman.springboot.domain.response.AFlyerResponse;
import com.usman.springboot.exception.WebClientApiException;
import com.usman.springboot.rest.client.RestClientApi;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 06-12-2022
 * @since : 1.0.0
 */
@Slf4j
@Component
public class AFlyerClientGateway {

    public static final String APPS_FLYER = "APPS_FLYER";
    @Autowired
    private RestClientApi restClientApi;

    private final String postUrl = "https://api2.appsflyer.com/inappevent/id5623091234";

    public AFlyerResponse callAppsFlyerApi(AFlyerRequest request) {

        try {
            log.info("callAppsFlyerApi restClient started...");
            String response = restClientApi.request(APPS_FLYER, HttpMethod.POST,
                postUrl, request, createHeaders(), String.class);
            log.info("response:{}", response);
            return new AFlyerResponse(response);
        } catch (Exception e) {
            log.error("Error found in appsFlyer API call: {}", e.getMessage());
            throw new WebClientApiException("ERR_404", e.getMessage());
        }
    }


    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.put("authentication", "Dwb93sHjgPqZXfsdf79kLg");
        return headers;
    }

}
