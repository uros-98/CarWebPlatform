package com.asss.zavrsni.rad.model;

import com.asss.zavrsni.rad.enums.OfferStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "offer_price", nullable = false)
    private BigDecimal offerPrice;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @JsonIgnore
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reservation reservation;
}