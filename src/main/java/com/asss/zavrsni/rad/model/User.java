package com.asss.zavrsni.rad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String address;

    private String phone;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    private Roles roles;

}