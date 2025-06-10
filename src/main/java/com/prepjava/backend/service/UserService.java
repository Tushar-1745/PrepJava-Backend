package com.prepjava.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prepjava.backend.model.User;
import com.prepjava.backend.repository.UserRepository;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    

    public User findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found with mobile number: " + mobileNumber));
    }
    
    public User loginUser(String username, String password){

        Optional<User> userOpt;

        if(username.contains("@")){
            userOpt = userRepository.findByEmail(username);
        }
        else{
            userOpt = userRepository.findByMobileNumber(username);
        }

        if (userOpt.isPresent()) {
            User foundUser = userOpt.get();


            if(foundUser.getPassword().equals(password)){
                return foundUser;
            }
            else{
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
}
