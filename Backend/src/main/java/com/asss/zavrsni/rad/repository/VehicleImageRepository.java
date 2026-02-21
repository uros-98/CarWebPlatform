package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleImageRepository extends JpaRepository<VehicleImage, Integer> {

    List<VehicleImage> findByVehicleId(int vehicleId);
}
