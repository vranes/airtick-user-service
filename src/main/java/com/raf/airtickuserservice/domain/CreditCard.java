package com.raf.airtickuserservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CreditCard {

    @Id
    private Long id;
    private int pin;            // TODO ogranicenje od 3 cifre
    private String FirstName;
    private String LastName;
    @ManyToOne
    private User user;
}
