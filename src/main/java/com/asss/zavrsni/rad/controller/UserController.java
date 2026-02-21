package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.RegisterRequestDTO;
import com.asss.zavrsni.rad.dto.UpdateUserDTO;
import com.asss.zavrsni.rad.enums.Roles;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public List<User> getUser() {
        return userService.findAllUsers();
    }

    @GetMapping("/get/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UpdateUserDTO updateUserDTO) {
        User update = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(update);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO, @RequestParam("role") String roleStr) {

        Roles role;
        try {
            role = Roles.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role: " + roleStr);
        }

        String result = userService.registerUser(registerRequestDTO, role);
        return ResponseEntity.ok(result);
    }
}
