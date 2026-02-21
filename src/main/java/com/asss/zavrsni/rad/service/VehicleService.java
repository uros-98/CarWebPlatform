package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.dto.UpdateVehicleDTO;
import com.asss.zavrsni.rad.dto.VehicleDTO;
import com.asss.zavrsni.rad.enums.Status;
import com.asss.zavrsni.rad.model.Category;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.model.VehicleImage;
import com.asss.zavrsni.rad.repository.CategoryRepository;
import com.asss.zavrsni.rad.repository.UserRepository;
import com.asss.zavrsni.rad.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Vehicle createVehicle(VehicleDTO vehicleDTO, List<MultipartFile> imageFiles) {
        Category category = categoryRepository.findById(vehicleDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        User seller = userRepository.findById(vehicleDTO.getSellerId())
                .orElseThrow(() -> new NoSuchElementException("Seller not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setMake(vehicleDTO.getMake());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setYear(vehicleDTO.getYear());
        vehicle.setMileage(vehicleDTO.getMileage());
        vehicle.setPrice(vehicleDTO.getPrice());
        vehicle.setDescription(vehicleDTO.getDescription());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setTransmission(vehicleDTO.getTransmission());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setEngineCapacity(vehicleDTO.getEngineCapacity());
        vehicle.setFeatures(vehicleDTO.getFeature());
        vehicle.setCategory(category);
        vehicle.setSeller(seller);
        vehicle.setStatus(Status.AVAILABLE);
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setUpdatedAt(LocalDateTime.now());
        vehicle.setImages(new ArrayList<>());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    try {
                        String uploadDir = "uploads/";
                        Files.createDirectories(Paths.get(uploadDir));

                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = Paths.get(uploadDir + fileName);
                        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                        VehicleImage vImage = new VehicleImage();
                        vImage.setImageUrl(fileName);
                        vImage.setVehicle(savedVehicle);

                        savedVehicle.getImages().add(vImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Error saving image: " + e.getMessage());
                    }
                }
            }
            return vehicleRepository.save(savedVehicle);
        }

        return savedVehicle;
    }

    public List<Vehicle> getAllAvailableVehicles() {
        return vehicleRepository.findByStatus(Status.AVAILABLE);
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle with ID: " + vehicleId + " not exists"));
    }

    public void deleteVehicleById(int vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new NoSuchElementException("Delete unavailable. Vehicle not exists");
        }

        vehicleRepository.deleteById(vehicleId);
    }

    public List<Vehicle> getVehiclesBySeller(int sellerId) {
        return vehicleRepository.findAllBySellerId(sellerId);
    }

    public Vehicle updateVehicle(int vehicleId, UpdateVehicleDTO updateVehicleDTO) {
        Vehicle vehicle = getVehicleById(vehicleId);

        vehicle.setMake(updateVehicleDTO.getMake());
        vehicle.setModel(updateVehicleDTO.getModel());
        vehicle.setYear(updateVehicleDTO.getYear());
        vehicle.setMileage(updateVehicleDTO.getMileage());
        vehicle.setPrice(updateVehicleDTO.getPrice());
        vehicle.setDescription(updateVehicleDTO.getDescription());
        vehicle.setFuelType(updateVehicleDTO.getFuelType());
        vehicle.setTransmission(updateVehicleDTO.getTransmission());
        vehicle.setColor(updateVehicleDTO.getColor());
        vehicle.setEngineCapacity(updateVehicleDTO.getEngineCapacity());

        return vehicleRepository.save(vehicle);
    }
}