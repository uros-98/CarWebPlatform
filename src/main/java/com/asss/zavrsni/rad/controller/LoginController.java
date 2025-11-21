package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.LoginRequestDTO;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final  LoginService loginService;

    @PostMapping("/log")
    ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            User user = loginService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
            return ResponseEntity.ok("Login success: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}