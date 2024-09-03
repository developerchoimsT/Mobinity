package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String Id);

    String findCodeByUserId(String Id);
}
