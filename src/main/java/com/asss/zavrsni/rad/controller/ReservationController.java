package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.CreateReservationDTO;
import com.asss.zavrsni.rad.model.Reservation;
import com.asss.zavrsni.rad.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationDTO createReservationDTO) {
        try {
            Reservation newResrvation = reservationService.createReservation(createReservationDTO);
            return new ResponseEntity<>(newResrvation, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred " + e.getMessage());
        }
    }

    @PutMapping("/{reservationId}/complete")
    public ResponseEntity<?> completeReservation(@PathVariable int reservationId) {
        try {
            Reservation reservation = reservationService.completeReservation(reservationId);
            return ResponseEntity.ok(reservation);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error completing the reservation " + e.getMessage());
        }
    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable int reservationId) {
        try {
            Reservation canceledReservation = reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok(canceledReservation);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling the reservation " + e.getMessage());
        }
    }
}