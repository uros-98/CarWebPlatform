package com.asss.zavrsni.rad.dto;

import com.asss.zavrsni.rad.enums.Roles;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String adress;
    private String phone;
    private Roles role;
}
