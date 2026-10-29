
package com.Java.Project.repo;

import com.Java.Project.model.clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface clientrepo
        extends JpaRepository<clients, Integer> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<clients> findByEmail(String email);
}
