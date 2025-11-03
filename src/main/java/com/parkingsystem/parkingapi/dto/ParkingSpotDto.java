package com.parkingsystem.parkingapi.dto;

import com.parkingsystem.parkingapi.domain.ParkingSpot;
import com.parkingsystem.parkingapi.domain.ParkingStatus;
import lombok.Data;

@Data
public class ParkingSpotDto {
    private Long id;
    private String spotLabel;
    private ParkingStatus status;

    public static ParkingSpotDto fromEntity(ParkingSpot spot) {
        ParkingSpotDto dto = new ParkingSpotDto();
        dto.setId(spot.getId());
        dto.setSpotLabel(spot.getSpotLabel());
        dto.setStatus(spot.getStatus());
        return dto;
    }
}