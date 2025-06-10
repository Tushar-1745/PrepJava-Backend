// package com.prepjava.backend.model;

// import java.time.LocalDateTime;

// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

// @Entity
// public class Solution {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(length = 65535) // To support large code blocks
//     private String code;

//     private String timeComplexity;
//     private String spaceComplexity;
//     private LocalDateTime timeStamp;

//     @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//     @JsonIgnore
//     @JoinColumn(name = "submission_id", nullable = false) // Ensure FK is non-null
//     private Submission submission;

//     // Default Constructor
//     public Solution() {
//     }

//     // Parameterized Constructor
//     public Solution(Long id, String code, String timeComplexity, String spaceComplexity, LocalDateTime timeStamp) {
//         this.id = id;
//         this.code = code;
//         this.timeComplexity = timeComplexity;
//         this.spaceComplexity = spaceComplexity;
//         this.timeStamp = timeStamp;
//     }

//     // Getters and Setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getCode() {
//         return code;
//     }

//     public void setCode(String code) {
//         this.code = code;
//     }

//     public String getTimeComplexity() {
//         return timeComplexity;
//     }

//     public void setTimeComplexity(String timeComplexity) {
//         this.timeComplexity = timeComplexity;
//     }

//     public String getSpaceComplexity() {
//         return spaceComplexity;
//     }

//     public void setSpaceComplexity(String spaceComplexity) {
//         this.spaceComplexity = spaceComplexity;
//     }

//     public LocalDateTime getTimeStamp() {
//         return timeStamp;
//     }

//     public void setTimeStamp(LocalDateTime timeStamp) {
//         this.timeStamp = timeStamp;
//     }

//     public Submission getSubmission() {
//         return submission;
//     }

//     public void setSubmission(Submission submission) {
//         this.submission = submission;
//     }
// }

package com.prepjava.backend.model;

import java.time.LocalDateTime;  // Add this import

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 65535)
    private String code;
    private String timeComplexity;
    private String spaceComplexity;
    private LocalDateTime timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    @JsonBackReference  // This annotation prevents the cyclic reference and stops serialization
    private Submission submission;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
