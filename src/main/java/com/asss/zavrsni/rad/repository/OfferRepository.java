package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    List<Offer> findByVehicleId(int vehicleId);

    List<Offer> findByBuyerId(int buyerId);

    List<Offer> findAllByVehicle_Seller_Id(int sellerId);
}
