package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.enums.Status;
import com.asss.zavrsni.rad.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByStatus(Status status);

    List<Vehicle> findBySellerId(int sellerId);

    @Query("SELECT v FROM Vehicle v " +
            "WHERE :make IS NULL OR v.price >= :minPrice AND " +
            ":maxPrice IS NULL OR v.price <= :maxPrice AND " +
            "v.status = 'AVAILABLE'")
    List<Vehicle> searchVehicles(String make, BigDecimal minPrice, BigDecimal maxPrice);

    List<Vehicle> findAllBySellerId(int sellerId);
}
