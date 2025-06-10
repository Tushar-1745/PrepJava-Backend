package com.prepjava.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prepjava.backend.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    // You can add custom queries if needed
    List<Submission> findSolvedProblemsByUserId(Long userId);

    Optional<Submission> findByUserIdAndProblemId(Long userId, Long problemId);


    List<Submission> findByUserId(Long userId);
}


