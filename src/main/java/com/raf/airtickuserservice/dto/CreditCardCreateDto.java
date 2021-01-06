package com.raf.airtickuserservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class CreditCardCreateDto {
    private Long id;
    @Min(value=0, message="must be 3 digits")
    @Max(value=999, message="must be 3 digits")
    private int pin;
    @JsonProperty("first_name")
    private String FirstName;
    @JsonProperty("last_name")
    private String LastName;
    private Long userId;
}
