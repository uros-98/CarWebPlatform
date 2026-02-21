package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.CreateOfferDTO;
import com.asss.zavrsni.rad.model.Offer;
import com.asss.zavrsni.rad.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/send-offer")
    public ResponseEntity<?> createOffer(@RequestBody CreateOfferDTO createOfferDTO) {
        try {
            Offer newOffer = offerService.createOffer(createOfferDTO);
            return new ResponseEntity<>(newOffer, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalAccessError e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred " + e.getMessage());
        }
    }

    @PutMapping("/{offerId}/accept")
    public ResponseEntity<?> acceptOffer(@PathVariable int offerId, @RequestParam int sellerId) {
        try {
            Offer acceptOffer = offerService.acceptOffer(offerId, sellerId);
            return ResponseEntity.ok(acceptOffer);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when accepting an offer " + e.getMessage());
        }
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<Offer>> getOfferForVehicle(@PathVariable("id") int vehicleId) {
        List<Offer> offers = offerService.getOfferForVehicle(vehicleId);
        if (offers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(offers);
    }

    @PutMapping("/{offerId}/reject")
    public ResponseEntity<?> rejectOffer(@PathVariable int offerId, @RequestParam int sellerId) {
        try {
            Offer rejectedOffer = offerService.rejectOffer(offerId, sellerId);
            return ResponseEntity.ok(rejectedOffer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    ResponseEntity<List<Offer>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffer());
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Offer>> getOffersBySeller(@PathVariable int sellerId) {
        return ResponseEntity.ok(offerService.getOffersForSeller(sellerId));
    }
}