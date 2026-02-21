package com.asss.zavrsni.rad.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    private String username;
    private String password;

}