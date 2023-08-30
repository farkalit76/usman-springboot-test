package com.usman.springboot.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 28-11-2022
 * @since : 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    protected String msgId;
    protected String value;
}
