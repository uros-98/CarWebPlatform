package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.AuthenticationResponse;
import com.asss.zavrsni.rad.dto.LoginRequestDTO;
import com.asss.zavrsni.rad.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/log")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.login(requestDTO));
    }
}
