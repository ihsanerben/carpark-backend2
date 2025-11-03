package com.parkingsystem.parkingapi.repository;

import com.parkingsystem.parkingapi.domain.ParkingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {

    Optional<ParkingTransaction> findByLicensePlateAndExitTimeIsNull(String licensePlate);

    List<ParkingTransaction> findByExitTimeIsNull();
}