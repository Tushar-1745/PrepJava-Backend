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

import com.prepjava.backend.dto.SQLSubmissionRequest;
import com.prepjava.backend.model.SQLSolution;
import com.prepjava.backend.model.SQLSubmission;
import com.prepjava.backend.service.SQLSubmissionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
// @CrossOrigin(origins = "http://192.168.41.96:3000", allowCredentials="true")
public class SQLSubmissionController {

    @Autowired
    private SQLSubmissionService SQLSubmissionService;

    @PostMapping("/sqlsubmission")


    public ResponseEntity<String> createSQLSubmission(@RequestBody SQLSubmissionRequest SQLSubmissionRequest) {
        System.out.println("posted sql submission request is "+ SQLSubmissionRequest);

        try {
            SQLSubmissionService.addSQLSolution(SQLSubmissionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Solution added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/solutionsofsolvedsqlproblem")
    public List<SQLSolution> solutionsofSolvedSQLProblem(@RequestParam Long userId, @RequestParam Long problemId) {

        return SQLSubmissionService.getSolutionsofSolvedSQLProblems(userId, problemId);
    }

    @GetMapping("/solvedsqlproblems")
    public List<SQLSubmission> getSolvedSQLProblems(@RequestParam Long userId) {
        System.out.println(SQLSubmissionService.getSolvedSQLProblemsOfUser(userId));
        return SQLSubmissionService.getSolvedSQLProblemsOfUser(userId);
    }

}
