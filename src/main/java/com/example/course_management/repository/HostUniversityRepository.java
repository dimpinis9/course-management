package com.example.course_management.repository;

import com.example.course_management.model.HostUniversity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostUniversityRepository extends JpaRepository<HostUniversity, Integer> {

    /**
     * Βρίσκει Host University με βάση το User ID
     */
    Optional<HostUniversity> findByUserId(Integer userId);

    /**
     * Βρίσκει Host University με βάση το όνομα
     */
    Optional<HostUniversity> findByUniversityName(String universityName);

    /**
     * Ελέγχει αν υπάρχει Host με το συγκεκριμένο userId
     */
    boolean existsByUserId(Integer userId);
}

