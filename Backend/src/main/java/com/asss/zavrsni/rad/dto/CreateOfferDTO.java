package com.asss.zavrsni.rad.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOfferDTO {
    private BigDecimal offerPrice;
    private String message;
    private int vehicleId;
    private int buyerId;
}
