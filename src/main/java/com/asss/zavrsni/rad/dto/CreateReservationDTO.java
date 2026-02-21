package com.asss.zavrsni.rad.dto;

import lombok.Data;

@Data
public class CreateReservationDTO {

    private int acceptedOfferId;
    private int daysToReserve = 3;
}
