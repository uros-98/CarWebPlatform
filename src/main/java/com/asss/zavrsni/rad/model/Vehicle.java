package com.asss.zavrsni.rad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String make;

    private String model;

    private int year;

    private int mileage;

    private double price;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private String fuelType;

    @Enumerated(EnumType.STRING)
    private String transmission;

    private String color;

    @Column(name = "engine_capacity")
    private double engineCapacity;

    private String features;

    @Enumerated(EnumType.STRING)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}