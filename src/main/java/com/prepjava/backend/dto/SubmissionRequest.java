package com.prepjava.backend.dto;

import java.time.LocalDateTime;

public class SubmissionRequest {

    private Long userId;
    private Long problemId;
    private SolutionRequest solution;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public SolutionRequest getSolution() {
        return solution;
    }

    public void setSolution(SolutionRequest solution) {
        this.solution = solution;
    }

    public static class SolutionRequest {
        private String code;
        private String timeComplexity;
        private String spaceComplexity;
        private LocalDateTime timeStamp;

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
            return timeStamp;
        }

        public void setTimeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
