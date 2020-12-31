package com.raf.airtickuserservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardCreateDto {
    private Long id;
    private int pin;
    @JsonProperty("first_name")
    private String FirstName;
    @JsonProperty("last_name")
    private String LastName;
    private Long userId;
}
