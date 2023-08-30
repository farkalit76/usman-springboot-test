package com.usman.springboot.web.controller;

import com.usman.springboot.annotation.LogExecutionTime;
import com.usman.springboot.domain.common.ApiResponse;
import com.usman.springboot.domain.request.MessageRequest;
import com.usman.springboot.domain.request.TestRequest;
import com.usman.springboot.domain.response.MessageResponse;
import com.usman.springboot.service.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 25-11-2022
 * @since : 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@LogExecutionTime
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @GetMapping(value = "/hello")
    public String getHelloMessage() {
        log.info("hello friend started....");
        return "Hello Friends!";
    }

    @PostMapping(value = "/message")
    public MessageResponse getMessage(@RequestBody MessageRequest message) {
        log.info("hello message started....");
        return new MessageResponse("101", message.getValue());
    }


    @GetMapping(value = "/call/getapi")
    public ApiResponse<Object> callGetAPI() {
        log.info("getAPI call started....");
        return ApiResponse.success(testService.callGetApi());
    }

    @PostMapping(value = "/call/postapi")
    public ApiResponse<Object> callPostAPI(@RequestBody TestRequest message) {
        log.info("postAPI call  started....");
        return ApiResponse.success(testService.callPostApi(message));
    }
}
