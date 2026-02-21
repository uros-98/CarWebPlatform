package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/vehicle/{vehicleId}")
    public ResponseEntity<String> toggleFavorite(@PathVariable int userId, @PathVariable int vehicleId) {
        try {
            favoriteService.toggleFavorite(userId, vehicleId);
            return ResponseEntity.ok("Favorite List is updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Set<Vehicle>> getFavorite(@PathVariable int userId) {
        try {
            Set<Vehicle> favorites = favoriteService.getUserFavorites(userId);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}