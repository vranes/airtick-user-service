package com.raf.airtickuserservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserRank {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;                          // TODO da li moze enum da bude id
    private Rank rank;
    private Integer minMiles;
    private Integer maxMiles;
    private Integer discount;

    public UserRank() {

    }

    public UserRank(Rank rank, Integer minMiles, Integer maxMiles, Integer discount) {
        this.rank = rank;
        this.minMiles = minMiles;
        this.maxMiles = maxMiles;
        this.discount = discount;
    }

}
