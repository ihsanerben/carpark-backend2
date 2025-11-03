package com.parkingsystem.parkingapi.dto;

import com.parkingsystem.parkingapi.domain.ParkingTransaction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CheckOutResponseDto {

    private Long transactionId;
    private String licensePlate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double fee;
    private String spotLabel;

    public static CheckOutResponseDto fromEntity(ParkingTransaction tx) {
        CheckOutResponseDto dto = new CheckOutResponseDto();
        dto.setTransactionId(tx.getId());
        dto.setLicensePlate(tx.getLicensePlate());
        dto.setEntryTime(tx.getEntryTime());
        dto.setExitTime(tx.getExitTime());
        dto.setFee(tx.getFee());
        dto.setSpotLabel(tx.getParkingSpot().getSpotLabel());
        return dto;
    }
}