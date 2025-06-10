package com.prepjava.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prepjava.backend.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom query methods here if needed

    public Optional<User> findByEmail(String email);

    public Optional<User> findByMobileNumber(String mobileNumber);

    @SuppressWarnings("null")
    @Override
    public Optional<User> findById(Long id);


}
