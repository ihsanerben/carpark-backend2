package com.parkingsystem.parkingapi.controller;

import com.parkingsystem.parkingapi.dto.CheckInRequestDto;
import com.parkingsystem.parkingapi.dto.CheckOutResponseDto;
import com.parkingsystem.parkingapi.dto.TransactionResponseDto;
import com.parkingsystem.parkingapi.domain.ParkingTransaction;
import com.parkingsystem.parkingapi.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.parkingsystem.parkingapi.dto.DashboardStatsDto;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<TransactionResponseDto> checkIn(@RequestBody CheckInRequestDto request) {

        ParkingTransaction newTransaction = parkingService.vehicleCheckIn(request.getLicensePlate());

        TransactionResponseDto responseDto = TransactionResponseDto.fromEntity(newTransaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/checkout/{licensePlate}")
    public ResponseEntity<CheckOutResponseDto> checkOut(@PathVariable String licensePlate) {

        ParkingTransaction updatedTransaction = parkingService.vehicleCheckOut(licensePlate);

        CheckOutResponseDto responseDto = CheckOutResponseDto.fromEntity(updatedTransaction);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/current")
    public ResponseEntity<List<TransactionResponseDto>> getCurrentlyParked() {

        List<ParkingTransaction> transactions = parkingService.getCurrentlyParkedVehicles();

        List<TransactionResponseDto> dtos = transactions.stream()
                .map(tx -> TransactionResponseDto.fromEntity(tx))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        DashboardStatsDto stats = parkingService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}