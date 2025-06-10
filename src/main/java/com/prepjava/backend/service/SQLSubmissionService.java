package com.prepjava.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prepjava.backend.dto.SQLSubmissionRequest;
import com.prepjava.backend.model.SQLSolution;
import com.prepjava.backend.model.SQLSubmission;
import com.prepjava.backend.repository.SQLSubmissionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SQLSubmissionService {

    @Autowired
    private SQLSubmissionRepository SQLSubmissionRepository;

    public List<SQLSolution> getSolutionsofSolvedSQLProblems(Long userId, Long problemId) {
        System.out.println("user is " + userId);
        System.out.println("problem is " + problemId);
    
        // Fetching the submission from the repository
        Optional<SQLSubmission> SQLSubmission = SQLSubmissionRepository.findByUserIdAndSqlProblemId(userId, problemId);
        System.out.println("submission is " + SQLSubmission);
    
        // Checking if the submission is present before calling getSolutions()
        if (SQLSubmission.isPresent()) {
            // Extract the Submission object
            SQLSubmission SQLSubmissionObject = SQLSubmission.get();
            
            // Fetch the list of solutions from the Submission object
            List<SQLSolution> SQLSolutionList = SQLSubmissionObject.getSqlSolutions();
            System.out.println("list is " + SQLSolutionList);
    
            // Returning the list of solutions
            return SQLSolutionList;
        } else {
            // Handle the case where the submission is not found
            System.out.println("No submission found for the given user and problem.");
            return new ArrayList<>(); // Return an empty list if no submission is found
        }
    }
    

    public List<SQLSubmission> getSolvedSQLProblemsOfUser(Long userId) {
        // Fetch the submissions for the given userId
        List<SQLSubmission> SQLSubmissions = SQLSubmissionRepository.findByUserId(userId);
    
        // Check if the list is empty and return an empty list if no submissions are found
        if (SQLSubmissions.isEmpty()) {
            System.out.println("No submissions found for the given user.");
            return new ArrayList<>(); // Return an empty list if no submissions are found
        }
    
        // Return the list of submissions
        return SQLSubmissions;
    }
    

    public void addSQLSolution(SQLSubmissionRequest request) {
        System.out.println("we are in sqlsubmission service");
        System.out.println("posted sql submission request is "+ request);
        System.out.println("user id is" + request.userId);
        System.out.println("problem id is "+ request.sqlProblemId);
        System.out.println(" solution query is"+ request.sqlSolution.query);
        System.out.println(" solution  timecomplexity is"+ request.sqlSolution.timeComplexity);
        System.out.println(" solution space complexity is"+ request.sqlSolution.spaceComplexity);
        System.out.println(" solution timeStamp is"+ request.sqlSolution.timeStamp);

        Optional<SQLSubmission> existingSubmission =
        SQLSubmissionRepository.findByUserIdAndSqlProblemId(request.getUserId(), request.getSQLProblemId());
    

        SQLSubmission SQLSubmission;
        if (existingSubmission.isPresent()) {
            SQLSubmission = existingSubmission.get();
            SQLSubmission.setTimeStamp(request.getSolution().getTimeStamp());
        } else {
            SQLSubmission = new SQLSubmission();
            SQLSubmission.setUserId(request.getUserId());
            SQLSubmission.setSQLProblemId(request.getSQLProblemId());
            SQLSubmission.setTimeStamp(request.getSolution().getTimeStamp());
        }

        SQLSolution SQLSolution = new SQLSolution();

        SQLSolution.setQuery(request.getSolution().getQuery());
        SQLSolution.setTimeComplexity(request.getSolution().getTimeComplexity());
        SQLSolution.setSpaceComplexity(request.getSolution().getSpaceComplexity());
        SQLSolution.setTimeStamp(request.getSolution().getTimeStamp());
        SQLSolution.setSqlSubmission(SQLSubmission);

        SQLSubmission.getSqlSolutions().add(SQLSolution);
        SQLSubmissionRepository.save(SQLSubmission);
    }
}
