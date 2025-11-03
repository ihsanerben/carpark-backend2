package com.parkingsystem.parkingapi.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parking_spots")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String spotLabel; // Örn: "A-01"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingStatus status;
}