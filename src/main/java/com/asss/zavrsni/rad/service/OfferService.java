package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.dto.CreateOfferDTO;
import com.asss.zavrsni.rad.enums.OfferStatus;
import com.asss.zavrsni.rad.enums.Status;
import com.asss.zavrsni.rad.model.Offer;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.repository.OfferRepository;
import com.asss.zavrsni.rad.repository.UserRepository;
import com.asss.zavrsni.rad.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public Offer createOffer(CreateOfferDTO request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new NoSuchElementException("Vehicle with ID: " + request + " doesn't contain"));

        if (vehicle.getStatus() != Status.AVAILABLE) {
            throw new IllegalArgumentException("Do not possible to send offer. Vehicle status is: " + vehicle.getStatus().name());
        }

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new NoSuchElementException("Buyer with ID: " + request.getBuyerId() + " does not exist"));

        Offer offer = new Offer();
        offer.setOfferPrice(request.getOfferPrice());
        offer.setMessage(request.getMessage());
        offer.setMessage(request.getMessage());
        offer.setVehicle(vehicle);
        offer.setBuyer(buyer);
        offer.setStatus(OfferStatus.WAITING);
        offer.setCreatedAt(LocalDateTime.now());
        offer.setUpdatedAt(LocalDateTime.now());

        Offer savedOffer = offerRepository.save(offer);

        String titleName = "New offer for your vehicle";
        String message = "Buyer sent offer for "
                + buyer.getUsername()
                + " | Price: " + request.getOfferPrice()
                + " | Vehicle: " + vehicle.getMake() + " " + vehicle.getModel();

        notificationService.sendNotification(vehicle.getSeller(), titleName, message);

        return savedOffer;
    }

    @Transactional
    public Offer acceptOffer(int offerId, int sellerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new NoSuchElementException("Offer with ID: " + offerId + " does not exist"));

        if (offer.getVehicle().getSeller().getId() != sellerId) {
            throw new SecurityException("You are not authorized to accept this offer.");
        }

        offer.setStatus(OfferStatus.ACCEPTED);
        offer.setUpdatedAt(LocalDateTime.now());

        Vehicle vehicle = offer.getVehicle();
        vehicle.setStatus(Status.RESERVED);
        vehicleRepository.save(vehicle);

        notificationService.sendNotification(
                offer.getBuyer(),
                "Offer Accepted!",
                "Great news! Your offer for " + vehicle.getMake() + " " + vehicle.getModel() + " has been accepted."
        );

        offerRepository.findByVehicleId(vehicle.getId()).stream()
                .filter(o -> o.getId() != offerId && o.getStatus() == OfferStatus.WAITING)
                .forEach(o -> {
                    o.setStatus(OfferStatus.DENIED);
                    o.setUpdatedAt(LocalDateTime.now());
                    offerRepository.save(o);
                    notificationService.sendNotification(o.getBuyer(), "Offer denied", "Your offer for " + vehicle.getMake() + " was rejected because another offer was accepted.");
                });

        return offerRepository.save(offer);
    }

    @Transactional
    public Offer rejectOffer(int offerId, int sellerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));

        if (offer.getVehicle().getSeller().getId() != sellerId) {
            throw new SecurityException("You are not authorized to reject this offer.");
        }

        if (offer.getStatus() != OfferStatus.WAITING) {
            throw new IllegalStateException("Only WAITING offers can be rejected.");
        }

        offer.setStatus(OfferStatus.DENIED);
        Offer savedOffer = offerRepository.save(offer);

        notificationService.sendNotification(
                offer.getBuyer(),
                "Offer Rejected",
                "Unfortunately, your offer for " + offer.getVehicle().getMake() + " was not accepted by the seller."
        );

        return savedOffer;
    }

    public List<Offer> getOfferForVehicle(int vehicleId) {
        return offerRepository.findByVehicleId(vehicleId);
    }

    public List<Offer> getAllOffer() {
        return offerRepository.findAll();
    }

    public List<Offer> getOffersForSeller(int sellerId) {
        return offerRepository.findAllByVehicle_Seller_Id(sellerId);
    }
}
