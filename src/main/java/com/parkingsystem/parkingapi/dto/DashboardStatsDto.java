package com.parkingsystem.parkingapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardStatsDto {
    private long totalSpots;
    private long occupiedSpots;
    private long availableSpots;
    private List<ParkingSpotDto> spots;
}