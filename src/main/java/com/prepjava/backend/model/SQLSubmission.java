package com.prepjava.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class SQLSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long sqlProblemId;
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy = "sqlSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SQLSolution> sqlSolutions = new ArrayList<>();  // Renamed for consistency

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSQLProblemId() {
        return sqlProblemId;
    }

    public void setSQLProblemId(Long sqlProblemId) {
        this.sqlProblemId = sqlProblemId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<SQLSolution> getSqlSolutions() {  // Renamed to match field name
        return sqlSolutions;
    }

    public void setSqlSolutions(List<SQLSolution> sqlSolutions) {
        this.sqlSolutions = sqlSolutions;
    }
}
