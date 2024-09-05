package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String id);
    User save(User user);
    Optional<User> findById(String Id);
}
