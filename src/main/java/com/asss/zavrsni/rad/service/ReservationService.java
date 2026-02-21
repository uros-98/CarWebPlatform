package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.dto.CreateReservationDTO;
import com.asss.zavrsni.rad.enums.OfferStatus;
import com.asss.zavrsni.rad.enums.ReservationStatus;
import com.asss.zavrsni.rad.enums.Status;
import com.asss.zavrsni.rad.model.Offer;
import com.asss.zavrsni.rad.model.Reservation;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.repository.OfferRepository;
import com.asss.zavrsni.rad.repository.ReservationRepository;
import com.asss.zavrsni.rad.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final OfferRepository offerRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    @Transactional
    public Reservation createReservation(CreateReservationDTO createReservationDTO) {
        Offer offer = offerRepository.findById(createReservationDTO.getAcceptedOfferId())
                .orElseThrow(() -> new NoSuchElementException("Offer with ID: " + createReservationDTO.getAcceptedOfferId() + " does not exist"));

        if (offer.getStatus() != OfferStatus.ACCEPTED) {
            throw new IllegalArgumentException("Reservation can create only for ACCEPTED offers");
        }

        if (offer.getReservation() != null) {
            throw new IllegalArgumentException("For current offer already exist reservation");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusDays(createReservationDTO.getDaysToReserve());

        Reservation reservation = new Reservation();
        reservation.setReservationDate(now);
        reservation.setExpirationDate(expirationDate);
        reservation.setTotalPrice(offer.getOfferPrice());
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(now);
        reservation.setOffer(offer);

        Reservation savedReservation = reservationRepository.save(reservation);

        notificationService.sendNotification(offer.getBuyer(), "Reservation created", "You are reserved vehicle successfully. Reservation during until " + expirationDate);
        notificationService.sendNotification(offer.getVehicle().getSeller(), "New reservation", "Vehicle " + offer.getVehicle().getMake() + " is reserved" + " The deadline is until " + expirationDate);

        return savedReservation;
    }

    @Transactional
    public Reservation completeReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation with " + reservationId + " does not exist"));

        reservation.setStatus(ReservationStatus.COMPLETED);

        Vehicle vehicle = reservation.getOffer().getVehicle();
        vehicle.setStatus(Status.SOLD);
        vehicleRepository.save(vehicle);

        notificationService.sendNotification(reservation.getOffer().getBuyer(), "You have completed your purchase", "Congratulations on the purchase of the vehicle");
        notificationService.sendNotification(vehicle.getSeller(), "Reservation cancelled", " Vehicle " + vehicle.getMake() + " is available again");

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancelReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation with " + reservationId + " does not exist"));

        if (reservation.getStatus() != ReservationStatus.CANCELLED) {
            throw new IllegalStateException("It possible to cancel only activated reservation.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        Vehicle vehicle = reservation.getOffer().getVehicle();
        vehicle.setStatus(Status.AVAILABLE);
        vehicleRepository.save(vehicle);

        notificationService.sendNotification(reservation.getOffer().getBuyer(), "Reservation cancelled", "The reservation has been cancelled. The vehicle is available for sale again");
        notificationService.sendNotification(vehicle.getSeller(), "Reservation cancelled", "Vehicle " + vehicle.getMake() + " is available again");

        return reservationRepository.save(reservation);
    }
}
