package com.asss.zavrsni.rad.dto;

import com.asss.zavrsni.rad.enums.FuelType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateVehicleDTO {

    private String make;
    private String model;
    private int year;
    private int mileage;
    private double price;
    private String description;
    private FuelType fuelType;
    private String transmission;
    private String color;
    private double engineCapacity;
    private LocalDateTime updatedAt;
}
