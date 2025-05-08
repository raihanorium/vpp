package com.raihanorium.vpp.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batteries")
@Getter
@Setter
public class Battery extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String postcode;
    private int capacity;
}
