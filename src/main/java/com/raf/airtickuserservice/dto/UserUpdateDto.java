package com.raf.airtickuserservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    @NotBlank
    private Long passport;
    @Email
    private String email;
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
    @Length(min = 8, max = 20)
    private String password;
    private Integer miles;

}
