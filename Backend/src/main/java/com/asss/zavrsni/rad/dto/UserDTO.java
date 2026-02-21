package com.asss.zavrsni.rad.dto;

import lombok.Data;

@Data
public class UserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String address;
    private String phone;
}
