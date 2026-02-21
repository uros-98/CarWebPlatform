package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.repository.UserRepository;
import com.asss.zavrsni.rad.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public void toggleFavorite(int userId, int vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));

        Set<Vehicle> favorites = user.getFavoriteVehicles();

        if (favorites.contains(vehicle)) {
            favorites.remove(vehicle);
        } else {
            favorites.add(vehicle);
        }

        userRepository.save(user);
    }

    public Set<Vehicle> getUserFavorites(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return user.getFavoriteVehicles();
    }
}
