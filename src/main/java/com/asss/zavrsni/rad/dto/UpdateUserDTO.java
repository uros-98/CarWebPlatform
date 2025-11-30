package com.asss.zavrsni.rad.dto;

import com.asss.zavrsni.rad.model.Roles;
import lombok.Data;
import java.util.Set;

@Data
public class UpdateUserDTO {
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String password;
        private String address;
        private String phone;
        private Set<Roles> roles;
    }
