package com.usman.springboot.service;

import com.usman.springboot.annotation.LogMethodExecutionTime;
import com.usman.springboot.domain.request.TestRequest;
import com.usman.springboot.domain.response.TestResponse;
import com.usman.springboot.rest.gateway.SampleRestClientBuilderGateway;
import com.usman.springboot.rest.gateway.SampleRestClientGateway;
import com.usman.springboot.rest.gateway.SampleWebClientGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@Service
public class TestServiceImpl {

//    @Autowired
//    private SampleRestClientGateway sampleClientGateway;

//    @Autowired
//    private SampleWebClientGateway sampleClientGateway;

    @Autowired
    //private SampleRestClientGateway sampleClientGateway;
    //private SampleWebClientGateway sampleClientGateway;
    private SampleRestClientBuilderGateway sampleClientGateway;

    @LogMethodExecutionTime
    public Object callGetApi() {
        log.info("callGetApi service");
        return sampleClientGateway.callGetApi();
    }

    public TestResponse callPostApi(TestRequest request) {
        log.info("callPostApi service");
        return sampleClientGateway.callPostApi(request);
    }
}
