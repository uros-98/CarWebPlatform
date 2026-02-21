package com.asss.zavrsni.rad.dto;

import com.asss.zavrsni.rad.enums.FuelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private String make;
    private String model;
    private int year;
    private int mileage;
    private double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private String transmission;
    private String color;
    private double engineCapacity;
    private String feature;
    private int categoryId;
    private int sellerId;
}
