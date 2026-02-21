package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.enums.Status;
import com.asss.zavrsni.rad.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByStatus(Status status);

    @Query("SELECT v FROM Vehicle v WHERE v.seller.id = :sellerId")
    List<Vehicle> findAllBySellerId(@Param("sellerId") int sellerId);

    @Query("SELECT v FROM Vehicle v " +
            "WHERE (:make IS NULL OR v.make = :make) AND " +
            "(:minPrice IS NULL OR v.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR v.price <= :maxPrice) AND " +
            "v.status = 'AVAILABLE'")
    List<Vehicle> searchVehicles(String make, BigDecimal minPrice, BigDecimal maxPrice);
}
