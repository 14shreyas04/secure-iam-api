
package com.Java.Project.service;

import com.Java.Project.DTO.RegisterRequest;
import com.Java.Project.DTO.RegisterResponse;
import com.Java.Project.model.clients;
import com.Java.Project.security.JwtService;
import com.Java.Project.repo.clientrepo;
import com.Java.Project.DTO.LoginRequest;
import com.Java.Project.DTO.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class clientService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final clientrepo repository;
    private final PasswordEncoder passwordEncoder;

    public clientService(
            JwtService jwtService, clientrepo repository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }



    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return new LoginResponse(
                "Login successful",
                request.email(),
                token
        );
    }



    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        if (repository.existsByUsername(request.username())) {
            throw new IllegalArgumentException(
                    "Username is already taken");
        }

        clients client = new clients();

        client.setUsername(request.username());
        client.setEmail(request.email());

        client.setPasswordHash(
                passwordEncoder.encode(request.password()));

        client.setStatus("ACTIVE");

        client.setRole("USER");

        clients saved = repository.save(client);

        return new RegisterResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getStatus()
        );
    }

    public List<clients> getall() {
        return repository.findAll();
    }
}
