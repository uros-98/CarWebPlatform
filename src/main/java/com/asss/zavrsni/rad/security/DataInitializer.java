package com.asss.zavrsni.rad.security;

import com.asss.zavrsni.rad.enums.Roles;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setUsername("admin");
            admin.setEmail(null);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAddress(null);
            admin.setPhone(null);
            admin.setRoles(Collections.singleton(Roles.ADMIN));
            userRepository.save(admin);

            System.out.println("Admin user created: username=admin, password=admin123");
        }
    }
}
