package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.UpdateVehicleDTO;
import com.asss.zavrsni.rad.dto.VehicleDTO;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(value = "/add-vehicle", consumes = { "multipart/form-data" })
    public ResponseEntity<Vehicle> createVehicle(@RequestPart("vehicle") VehicleDTO vehicleDTO, @RequestPart("images") List<MultipartFile> images) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicleDTO, images));
    }

    @GetMapping("/get-all-vehicle")
    public ResponseEntity<List<Vehicle>> getAllAvailableVehicle() {
        return ResponseEntity.ok(vehicleService.getAllAvailableVehicles());
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int vehicleId) {
        return ResponseEntity.ok(vehicleService.getVehicleById(vehicleId));
    }

    @DeleteMapping("/delete-vehicle/{vehicleId}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable int vehicleId) {
        vehicleService.deleteVehicleById(vehicleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesBySeller(@PathVariable int sellerId) {
        return ResponseEntity.ok(vehicleService.getVehiclesBySeller(sellerId));
    }

    @PutMapping("/update-vehicle/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable int vehicleId, @RequestBody UpdateVehicleDTO updateVehicleDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleId, updateVehicleDTO));
    }
}