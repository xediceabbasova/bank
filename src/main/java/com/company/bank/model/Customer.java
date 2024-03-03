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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


}
