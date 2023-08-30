package com.usman.springboot.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Data
public class TestRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
