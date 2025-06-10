package com.prepjava.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prepjava.backend.dto.SubmissionRequest;
import com.prepjava.backend.model.Solution;
import com.prepjava.backend.model.Submission;
import com.prepjava.backend.service.SubmissionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
// @CrossOrigin(origins = "http://192.168.41.96:3000", allowCredentials="true")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submission")
    public ResponseEntity<String> createSubmission(@RequestBody SubmissionRequest submissionRequest) {
        try {
            submissionService.addSolution(submissionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Solution added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/solutionsofsolvedproblem")
    public List<Solution> solutionsofSolvedProblem(@RequestParam Long userId, @RequestParam Long problemId) {



        return submissionService.getSolutionsofSolvedProblem(userId, problemId);
    }

    @GetMapping("/solvedproblems")
    public List<Submission> getSolvedProblems(@RequestParam Long userId) {
        System.out.println(submissionService.getSolvedProblemsOfUser(userId));
        return submissionService.getSolvedProblemsOfUser(userId);
    }

}
