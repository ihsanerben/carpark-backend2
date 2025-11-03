package com.parkingsystem.parkingapi.service;

import com.parkingsystem.parkingapi.domain.ParkingTransaction;

import com.parkingsystem.parkingapi.dto.DashboardStatsDto;
import com.parkingsystem.parkingapi.domain.ParkingSpot;

import java.util.List;

public interface ParkingService {

    ParkingTransaction vehicleCheckIn(String licensePlate);

    ParkingTransaction vehicleCheckOut(String licensePlate);

    List<ParkingTransaction> getCurrentlyParkedVehicles();

    DashboardStatsDto getDashboardStats();


}