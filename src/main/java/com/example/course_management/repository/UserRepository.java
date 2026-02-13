package com.example.course_management.repository;

import com.example.course_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Βρίσκει User με βάση το username
     */
    Optional<User> findByUsername(String username);

    /**
     * Βρίσκει User με βάση το email
     */
    Optional<User> findByEmail(String email);

    /**
     * Ελέγχει αν υπάρχει username
     */
    boolean existsByUsername(String username);

    /**
     * Ελέγχει αν υπάρχει email
     */
    boolean existsByEmail(String email);
}

