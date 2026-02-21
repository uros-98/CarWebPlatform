package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.enums.ReservationStatus;
import com.asss.zavrsni.rad.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByOfferId(int offerId);

    List<Reservation> findByStatus(ReservationStatus status);
}