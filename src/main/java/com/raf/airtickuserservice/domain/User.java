package com.raf.airtickuserservice.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(columnList = "username", unique = true), @Index(columnList = "email", unique = true)})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long passport;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<CreditCard> cards = new ArrayList<>();
    private Integer miles;
    @ManyToOne(optional = false)
    private Role role;
}
