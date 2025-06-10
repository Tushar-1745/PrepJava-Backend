package com.prepjava.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prepjava.backend.dto.UpdateProfileRequest;
import com.prepjava.backend.model.User;
import com.prepjava.backend.service.UserService;
import com.prepjava.backend.util.JwtUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

// @CrossOrigin(origins = "http://192.168.41.96:3000", allowCredentials="true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {

            User savedUser = userService.saveUser(user);  // Save user to database
            return ResponseEntity.status(201).body(savedUser);  // Return user with status 201
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Return error if user cannot be saved
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        try {
            System.out.println("Attempting login with username: " + username);

            // Authenticate user
            User authenticatedUser = userService.loginUser(username, password);

            // Generate token
            String token = JwtUtil.generateToken(authenticatedUser.getEmail());

            // Create response map
            Map<String, Object> response = new HashMap<>();
            response.put("user", authenticatedUser);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/getprofile")
    public ResponseEntity<?> getMethodName(@RequestParam String username) {
        User userProfile;
        try {
            if (username.contains("@")) {
                userProfile = userService.findByEmail(username);
            } else {
                userProfile = userService.findByMobileNumber(username);
            }
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    // Upload profile picture
    @PostMapping("/{userId}/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@PathVariable String username, @RequestParam("photo") MultipartFile photo) {
        User user;
        try {
            if (username.contains("@")) {
                user = (User) userService.findByEmail(username);
            } else {
                user = (User) userService.findByMobileNumber(username);
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Create directories if not exist
            Files.createDirectories(filePath.getParent());

            // Save file
            Files.write(filePath, photo.getBytes());

            // Update user photo URL and save
            user.setPhoto("/profile_pictures/" + fileName);
            userService.saveUser(user);

            return ResponseEntity.ok("Photo uploaded successfully: " + user.getPhoto());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo");
        }
    }

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profile_pictures/";

    // Retrieve profile picture
    @GetMapping("/uploads/profile_pictures/{filename}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource fileResource = new UrlResource(filePath.toUri());

            if (fileResource.exists() || fileResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // You can dynamically determine the type
                        .body(fileResource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{userId}/updateprofile")
    public ResponseEntity<String> updateProfile(
            @PathVariable Long userId,
            @ModelAttribute UpdateProfileRequest request) {
        try {
            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Update basic details
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }

            // Handle profile picture upload
            MultipartFile photo = request.getPhoto();
            if (photo != null && !photo.isEmpty()) {

                // Validate file type
                if (!photo.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("Invalid file type. Only images are allowed.");
                }

                // Validate file size (Max 2MB)
                if (photo.getSize() > 2 * 1024 * 1024) {
                    return ResponseEntity.badRequest().body("File size must be less than 2MB.");
                }

                // Ensure directory exists
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                // Generate unique filename
                String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName).normalize();

                // Save file
                Files.write(filePath, photo.getBytes());

                // Update user photo path in DB
                user.setPhoto("/uploads/profile_pictures/" + fileName);
            }

            userService.saveUser(user);
            return ResponseEntity.ok("Profile updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating profile: " + e.getMessage());
        }
    }

}
