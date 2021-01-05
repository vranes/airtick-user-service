package com.raf.airtickuserservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CreditCardDto {
    private Long id;
    @Size(max = 999 , min = 100)
    private int pin;
    @JsonProperty("first_name")
    private String FirstName;
    @JsonProperty("last_name")
    private String LastName;
    private Long userId;
}
