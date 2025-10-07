package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.LoginRequest;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/log")
    ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok("Login success: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
