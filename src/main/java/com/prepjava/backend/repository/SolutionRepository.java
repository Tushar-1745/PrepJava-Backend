package com.prepjava.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prepjava.backend.model.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
    // You can add custom queries if needed
}

