package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public User updateUser(int id, User newData) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            System.out.println("User not found");
        } else {
            user.setFirstName(newData.getFirstName());
            user.setLastName(newData.getLastName());
            user.setUsername(newData.getUsername());
            user.setEmail(newData.getEmail());
            user.setPassword(passwordEncoder.encode(newData.getPassword()));
            user.setAddress(newData.getAddress());
            user.setPhone(newData.getPhone());
        }

        return userRepository.save(user);
    }
}