package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    default String findRoleByUserId(String id){
        Users users = findByUsersId(id);
        String role = users.getUsersRole();
        return role;
    }

    Users findByUsersId(String id);

}
