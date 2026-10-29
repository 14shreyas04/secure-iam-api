
package com.Java.Project.DTO;

public record LoginResponse(
        String message,
        String email,
        String token
) {}
