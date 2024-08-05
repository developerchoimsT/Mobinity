package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    Users findByUsersId(String id);

    default String findRoleByUserId(String id) {
        Users users = findByUsersId(id);
        return users.getUsersRole();
    }

}
