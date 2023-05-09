package com.github.rsoi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_table")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String phoneName;
    String producer;
    int phoneMinPrice;
    int phoneMaxPrice;
    double screenSize;
    int rammax;
    int rammin;
    String hasSdCardSlot;
    String imageLink;
}
