package com.asss.zavrsni.rad.security;

import com.asss.zavrsni.rad.dto.AuthenticationResponse;
import com.asss.zavrsni.rad.dto.LoginRequestDTO;
import com.asss.zavrsni.rad.enums.Roles;
import com.asss.zavrsni.rad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        var user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        var userDetails = new CustomUserDetails(user);
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);

        String roleName = user.getRoles().stream()
                .findFirst()
                .map(Roles::name)
                .orElse("USER");

        return new AuthenticationResponse(
                jwtToken,
                user.getUsername(),
                roleName
        );
    }
}
