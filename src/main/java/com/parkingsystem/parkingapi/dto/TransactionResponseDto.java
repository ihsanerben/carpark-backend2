package com.parkingsystem.parkingapi.dto;

import com.parkingsystem.parkingapi.domain.ParkingTransaction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private String licensePlate;
    private LocalDateTime entryTime;
    private String spotLabel;

    public static TransactionResponseDto fromEntity(ParkingTransaction tx) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setTransactionId(tx.getId());
        dto.setLicensePlate(tx.getLicensePlate());
        dto.setEntryTime(tx.getEntryTime());
        dto.setSpotLabel(tx.getParkingSpot().getSpotLabel()); // Düzleştirme (Flattening)
        return dto;
    }
}