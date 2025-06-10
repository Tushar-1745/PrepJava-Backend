package com.prepjava.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prepjava.backend.model.SQLSubmission;

public interface SQLSubmissionRepository extends JpaRepository<SQLSubmission, Long> {
    List<SQLSubmission> findSolvedProblemsByUserId(Long userId);

    Optional<SQLSubmission> findByUserIdAndSqlProblemId(Long userId, Long sqlProblemId);

    List<SQLSubmission> findByUserId(Long userId);
}

