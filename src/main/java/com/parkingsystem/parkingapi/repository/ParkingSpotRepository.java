package com.parkingsystem.parkingapi.repository;

import com.parkingsystem.parkingapi.domain.ParkingSpot;
import com.parkingsystem.parkingapi.domain.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    List<ParkingSpot> findByStatus(ParkingStatus status);

    Optional<ParkingSpot> findBySpotLabel(String spotLabel);

    long countByStatus(ParkingStatus status);
}