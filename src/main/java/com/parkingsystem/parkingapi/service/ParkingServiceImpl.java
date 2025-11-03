package com.parkingsystem.parkingapi.service;

import com.parkingsystem.parkingapi.domain.ParkingSpot;
import com.parkingsystem.parkingapi.domain.ParkingStatus;
import com.parkingsystem.parkingapi.domain.ParkingTransaction;
import com.parkingsystem.parkingapi.exception.ParkingException;
import com.parkingsystem.parkingapi.repository.ParkingSpotRepository;
import com.parkingsystem.parkingapi.repository.ParkingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.parkingsystem.parkingapi.dto.DashboardStatsDto;
import com.parkingsystem.parkingapi.dto.ParkingSpotDto;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.time.Duration;

@Service
public class ParkingServiceImpl implements ParkingService {

    private static final double HOURLY_FEE = 20.0;

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingTransactionRepository parkingTransactionRepository;

    public ParkingServiceImpl(ParkingSpotRepository parkingSpotRepository,
                              ParkingTransactionRepository parkingTransactionRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingTransactionRepository = parkingTransactionRepository;
    }

    @Override
    @Transactional
    public ParkingTransaction vehicleCheckIn(String licensePlate) {

        Optional<ParkingTransaction> existingTx = parkingTransactionRepository.findByLicensePlateAndExitTimeIsNull(licensePlate);

        if (existingTx.isPresent()) {
            throw new ParkingException("Plaka '" + licensePlate + "' zaten otoparkta.");
        }

        List<ParkingSpot> availableSpots = parkingSpotRepository.findByStatus(ParkingStatus.AVAILABLE);

        if (availableSpots.isEmpty()) {
            throw new ParkingException("Otopark dolu. Boş yer yok.");
        }

        ParkingSpot spotToOccupy = availableSpots.get(0);
        spotToOccupy.setStatus(ParkingStatus.OCCUPIED);
        parkingSpotRepository.save(spotToOccupy);


        ParkingTransaction newTransaction = new ParkingTransaction();
        newTransaction.setLicensePlate(licensePlate);
        newTransaction.setEntryTime(LocalDateTime.now());
        newTransaction.setParkingSpot(spotToOccupy);

        return parkingTransactionRepository.save(newTransaction);
    }


    @Override
    @Transactional
    public ParkingTransaction vehicleCheckOut(String licensePlate) {

        ParkingTransaction transaction = parkingTransactionRepository
                .findByLicensePlateAndExitTimeIsNull(licensePlate)
                .orElseThrow(() ->
                        new ParkingException("Plaka '" + licensePlate + "' otoparkta bulunamadı veya zaten çıkış yapmış.")
                );


        LocalDateTime exitTime = LocalDateTime.now();
        ParkingSpot spot = transaction.getParkingSpot();

        Duration duration = Duration.between(transaction.getEntryTime(), exitTime);

        long hoursParked = duration.toHours() + (duration.toMinutesPart() > 0 ? 1 : 0);

        if (hoursParked == 0) {
            hoursParked = 1;
        }

        double fee = hoursParked * HOURLY_FEE;

        transaction.setExitTime(exitTime);
        transaction.setFee(fee);

        spot.setStatus(ParkingStatus.AVAILABLE);

        parkingSpotRepository.save(spot);
        return parkingTransactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingTransaction> getCurrentlyParkedVehicles() {

        return parkingTransactionRepository.findByExitTimeIsNull();
    }


    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStats() {

        long totalSpots = parkingSpotRepository.count();
        long occupiedSpots = parkingSpotRepository.countByStatus(ParkingStatus.OCCUPIED);
        long availableSpots = totalSpots - occupiedSpots;

        List<ParkingSpotDto> spotDtos = parkingSpotRepository.findAll()
                .stream() // Java 8 Stream API
                .map(spot -> ParkingSpotDto.fromEntity(spot))
                .collect(Collectors.toList());

        // Tüm verileri ana DTO'ya doldur
        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setTotalSpots(totalSpots);
        stats.setOccupiedSpots(occupiedSpots);
        stats.setAvailableSpots(availableSpots);
        stats.setSpots(spotDtos);

        return stats;
    }
}