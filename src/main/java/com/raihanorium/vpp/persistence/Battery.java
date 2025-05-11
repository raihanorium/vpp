package com.raihanorium.vpp.persistence;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "battery_seq")
    @SequenceGenerator(name = "battery_seq", sequenceName = "battery_sequence")
    private Long id;
    private String name;
    private String postcode;
    private int capacity;
}
