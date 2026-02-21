package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.model.VehicleImage;
import com.asss.zavrsni.rad.service.VehicleImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class VehicleImageController {

    private final VehicleImageService vehicleImageService;

    @PostMapping("/upload-images/{vehicleId}")
    public ResponseEntity<?> uploadImage(@PathVariable int vehicleId, @RequestParam("file")MultipartFile file) {
        try {
            VehicleImage savedImage = vehicleImageService.uploadImage(vehicleId, file);
            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error by uploading image " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteImage(@PathVariable int imageId) {
        try {
            vehicleImageService.deleteImage(imageId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error by deleting image " + e.getMessage());
        }
    }
}