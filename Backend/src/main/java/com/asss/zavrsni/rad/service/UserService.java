package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.dto.RegisterRequestDTO;
import com.asss.zavrsni.rad.dto.UpdateUserDTO;
import com.asss.zavrsni.rad.enums.Roles;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(int id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            System.out.println("User not found");
        } else {
            user.setFirstName(updateUserDTO.getFirstName());
            user.setLastName(updateUserDTO.getLastName());
            user.setUsername(updateUserDTO.getUsername());
            user.setEmail(updateUserDTO.getEmail());
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            user.setAddress(updateUserDTO.getAddress());
            user.setPhone(updateUserDTO.getPhone());
            if (updateUserDTO.getRoles() != null && !updateUserDTO.getRoles().isEmpty()) {
                user.setRoles(updateUserDTO.getRoles());
            }
        }

        return userRepository.save(user);
    }

    public String registerUser(RegisterRequestDTO registerRequestDTO, Roles role) {

        if (role == Roles.ADMIN) {
            throw new IllegalArgumentException("Unauthorized attempt to register as ADMIN.");
        }

        User user = new User();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setAddress(registerRequestDTO.getAdress());
        user.setPhone(registerRequestDTO.getPhone());
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return role + " is registreted successfully";
    }
}