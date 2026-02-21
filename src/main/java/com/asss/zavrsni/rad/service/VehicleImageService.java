package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.model.VehicleImage;
import com.asss.zavrsni.rad.repository.VehicleImageRepository;
import com.asss.zavrsni.rad.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleImageService {

    private static final String UPLOAD_DIR = "uploads/vehicles";

    private final VehicleRepository vehicleRepository;
    private final VehicleImageRepository vehicleImageRepository;

    public VehicleImage uploadImage(int vehicleId, MultipartFile file) throws IOException {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));

        Path copyLocation = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        if (!Files.exists(copyLocation)) {
            Files.createDirectories(copyLocation);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetPath = copyLocation.resolve(fileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        VehicleImage vehicleImage = new VehicleImage();
        vehicleImage.setImageUrl("/" + UPLOAD_DIR + "/" + fileName);
        vehicleImage.setVehicle(vehicle);

        return vehicleImageRepository.save(vehicleImage);
    }

    public void deleteImage(int imageId) throws IOException {
        VehicleImage image = vehicleImageRepository.findById(imageId)
                .orElseThrow(() -> new NoSuchElementException("Image not found"));

        String imageUrl = image.getImageUrl();
        String pathOnDisk = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;

        Path path = Paths.get(pathOnDisk).toAbsolutePath().normalize();

        Files.deleteIfExists(path);

        vehicleImageRepository.delete(image);
    }
}