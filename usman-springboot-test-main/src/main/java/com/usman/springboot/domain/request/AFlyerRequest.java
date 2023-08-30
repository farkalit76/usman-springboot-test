package com.usman.springboot.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.usman.springboot.annotation.EnumValidator;
import com.usman.springboot.domain.enums.Enums;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 06-12-2022
 * @since : 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AFlyerRequest {

    @JsonProperty("aflyer_id")
    private String aflyerId;

    @NotBlank
    @NotBlank
    @EnumValidator(enumClass = Enums.Events.class, message = "Invalid event type")
    private String eventName;

    @NotBlank
    private String eventValue;

}
