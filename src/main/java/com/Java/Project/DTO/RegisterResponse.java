package com.Java.Project.DTO;

public record RegisterResponse(
        int id,
        String username,
        String email,
        String status
) {}
