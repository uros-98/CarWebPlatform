package com.asss.zavrsni.rad.model;

import com.asss.zavrsni.rad.dto.UpdateVehicleDTO;
import com.asss.zavrsni.rad.enums.FuelType;
import com.asss.zavrsni.rad.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String make;

    private String model;

    private int year;

    private int mileage;

    private double price;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    private String transmission;

    private String color;

    @Column(name = "engine_capacity")
    private double engineCapacity;

    private String features;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VehicleImage> images;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations;

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteVehicles")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> favoritedBy = new HashSet<>();

    public void updateVehicle(UpdateVehicleDTO updateVehicleDTO) {
        this.make = updateVehicleDTO.getMake();
        this.model = updateVehicleDTO.getModel();
        this.year = updateVehicleDTO.getYear();
        this.mileage = updateVehicleDTO.getMileage();
        this.price = updateVehicleDTO.getPrice();
        this.description = updateVehicleDTO.getDescription();
        this.fuelType = updateVehicleDTO.getFuelType();
        this.transmission = updateVehicleDTO.getTransmission();
        this.color = updateVehicleDTO.getColor();
        this.engineCapacity = updateVehicleDTO.getEngineCapacity();
        this.updatedAt = LocalDateTime.now();
    }
}