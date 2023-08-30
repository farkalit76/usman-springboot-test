package com.usman.springboot.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("token")
    private String token;
}
