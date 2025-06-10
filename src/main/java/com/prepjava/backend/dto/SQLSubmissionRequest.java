package com.prepjava.backend.dto;

import java.time.LocalDateTime;

public class SQLSubmissionRequest {

    public Long userId;
    public Long sqlProblemId;
    public SQLSolutionRequest sqlSolution;

    // Getters and Setters
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

    public SQLSolutionRequest getSolution() {
        return sqlSolution;
    }

    public void setSolution(SQLSolutionRequest sqlSolution) {
        this.sqlSolution = sqlSolution;
    }

    public static class SQLSolutionRequest {
        public String query;
        public String timeComplexity;
        public String spaceComplexity;
        public LocalDateTime timeStamp;

        // Getters and Setters
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getTimeComplexity() {
            return timeComplexity;
        }

        public void setTimeComplexity(String timeComplexity) {
            this.timeComplexity = timeComplexity;
        }

        public String getSpaceComplexity() {
            return spaceComplexity;
        }

        public void setSpaceComplexity(String spaceComplexity) {
            this.spaceComplexity = spaceComplexity;
        }

        public LocalDateTime getTimeStamp() {
            if (timeStamp == null) {
                timeStamp = LocalDateTime.now();
            }
            return timeStamp;
        }

        public void setTimeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
