package com.prepjava.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prepjava.backend.dto.SubmissionRequest;
import com.prepjava.backend.model.Solution;
import com.prepjava.backend.model.Submission;
import com.prepjava.backend.repository.SubmissionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public List<Solution> getSolutionsofSolvedProblem(Long userId, Long problemId) {
        System.out.println("user is " + userId);
        System.out.println("problem is " + problemId);
    
        // Fetching the submission from the repository
        Optional<Submission> submission = submissionRepository.findByUserIdAndProblemId(userId, problemId);
        System.out.println("submission is " + submission);
    
        // Checking if the submission is present before calling getSolutions()
        if (submission.isPresent()) {
            // Extract the Submission object
            Submission submissionObject = submission.get();
            
            // Fetch the list of solutions from the Submission object
            List<Solution> solutionList = submissionObject.getSolutions();
            System.out.println("list is " + solutionList);
    
            // Returning the list of solutions
            return solutionList;
        } else {
            // Handle the case where the submission is not found
            System.out.println("No submission found for the given user and problem.");
            return new ArrayList<>(); // Return an empty list if no submission is found
        }
    }
    

    public List<Submission> getSolvedProblemsOfUser(Long userId) {
        // Fetch the submissions for the given userId
        List<Submission> submissions = submissionRepository.findByUserId(userId);
    
        // Check if the list is empty and return an empty list if no submissions are found
        if (submissions.isEmpty()) {
            System.out.println("No submissions found for the given user.");
            return new ArrayList<>(); // Return an empty list if no submissions are found
        }
    
        // Return the list of submissions
        return submissions;
    }
    

    public void addSolution(SubmissionRequest request) {
        Optional<Submission> existingSubmission =
        submissionRepository.findByUserIdAndProblemId(request.getUserId(), request.getProblemId());
    

        Submission submission;
        if (existingSubmission.isPresent()) {
            submission = existingSubmission.get();
            submission.setTimeStamp(request.getSolution().getTimeStamp());
        } else {
            submission = new Submission();
            submission.setUserId(request.getUserId());
            submission.setProblemId(request.getProblemId());
            submission.setTimeStamp(request.getSolution().getTimeStamp());
        }

        Solution solution = new Solution();
        solution.setCode(request.getSolution().getCode());
        solution.setTimeComplexity(request.getSolution().getTimeComplexity());
        solution.setSpaceComplexity(request.getSolution().getSpaceComplexity());
        solution.setTimeStamp(request.getSolution().getTimeStamp());
        solution.setSubmission(submission);

        submission.getSolutions().add(solution);
        submissionRepository.save(submission);
    }
}
