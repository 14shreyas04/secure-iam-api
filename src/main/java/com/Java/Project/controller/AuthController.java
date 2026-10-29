
package com.Java.Project.controller;

import com.Java.Project.DTO.LoginRequest;
import com.Java.Project.DTO.LoginResponse;
import org.springframework.security.core.Authentication;
import java.util.Map;
import com.Java.Project.DTO.RegisterRequest;
import com.Java.Project.DTO.RegisterResponse;
import com.Java.Project.service.clientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final clientService clientSer;

    public AuthController(clientService clientSer) {
        this.clientSer = clientSer;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return clientSer.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                clientSer.login(request)
        );
    }

    @GetMapping("/me")
    public Map<String, String> me(Authentication authentication) {
        return Map.of(
                "message", "JWT authentication successful",
                "email", authentication.getName()
        );
    }

}
