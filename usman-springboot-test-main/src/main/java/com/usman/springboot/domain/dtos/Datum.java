package com.usman.springboot.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Datum {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("email")
    public String email;
    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    @JsonProperty("avatar")
    public String avatar;

}
