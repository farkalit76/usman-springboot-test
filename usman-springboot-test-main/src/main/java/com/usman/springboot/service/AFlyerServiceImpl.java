package com.usman.springboot.service;

import com.usman.springboot.domain.request.AFlyerRequest;
import com.usman.springboot.domain.response.AFlyerResponse;
import com.usman.springboot.rest.gateway.AFlyerClientGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 06-12-2022
 * @since : 1.0.0
 */
@Slf4j
@Service
public class AFlyerServiceImpl {

    @Autowired
    private AFlyerClientGateway appsFlyerClientGateway;

    public AFlyerResponse callAppsFlyerApi(AFlyerRequest request) {
        log.info("callAppsFlyerApi service started...");
        return appsFlyerClientGateway.callAppsFlyerApi(request);
    }
}
