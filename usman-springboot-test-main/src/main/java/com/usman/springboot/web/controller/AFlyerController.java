package com.usman.springboot.web.controller;

import com.usman.springboot.domain.request.AFlyerRequest;
import com.usman.springboot.domain.response.AFlyerResponse;
import com.usman.springboot.service.AFlyerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 06-12-2022
 * @since : 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AFlyerController {

    @Autowired
    private AFlyerServiceImpl appsFlyerService;

    @PostMapping(value = "/appsflyer")
    public AFlyerResponse getAppsFlyer(@RequestBody AFlyerRequest request) {
        log.info("getAppsFlyer controller started....");
        appsFlyerService.callAppsFlyerApi(request);
        return new AFlyerResponse("OK");
    }
}
