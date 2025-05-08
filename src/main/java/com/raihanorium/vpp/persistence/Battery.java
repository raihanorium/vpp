package com.raihanorium.vpp.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Battery extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String postcode;
    private int capacity;
}
