package com.prepjava.backend.dto;
import org.springframework.web.multipart.MultipartFile;

public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private MultipartFile photo; // Handle image upload

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public MultipartFile getPhoto() { return photo; }
    public void setPhoto(MultipartFile photo) { this.photo = photo; }
}
