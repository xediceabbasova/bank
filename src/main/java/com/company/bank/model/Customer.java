package com.company.bank.model;


import javax.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Customer {

    @Id
    private String id;

    private String name;
    private Integer dateOfBirth;
    private City city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Address> address;


}
