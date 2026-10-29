
package com.Java.Project.service;

import com.Java.Project.model.clients;
import com.Java.Project.repo.clientrepo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final clientrepo repository;

    public CustomUserDetailsService(clientrepo repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        clients client = repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        return User.builder()
                .username(client.getEmail())
                .password(client.getPasswordHash())
                .roles(client.getRole())
                .build();
    }
}
