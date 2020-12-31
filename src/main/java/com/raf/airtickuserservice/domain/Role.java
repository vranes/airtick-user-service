package com.raf.airtickuserservice.domain;

import lombok.Setter;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Role() {}

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
