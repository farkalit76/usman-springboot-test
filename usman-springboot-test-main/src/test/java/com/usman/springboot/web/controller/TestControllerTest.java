package com.usman.springboot.web.controller;

import com.usman.springboot.domain.request.MessageRequest;
import com.usman.springboot.domain.response.MessageResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-11-2022
 * @since : 1.0.0
 */
@SpringBootTest
public class TestControllerTest {
    @InjectMocks
    private TestController testController;

    @Test
    void testGetHelloMessage() {
         String helloMessage = testController.getHelloMessage();
        assertNotNull(helloMessage);
    }

    @Test
    void testGetMessage() {
        MessageRequest request = new MessageRequest();
        MessageResponse message = testController.getMessage(request);
        assertNotNull(message);
    }
}
