package com.ms.hoopi.join;

import com.ms.hoopi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    default String findRoleByUserId(String id){
        System.out.println(id);
        Users users = findByUsersId(id);
        String role = users.getUsersRole();
        return role;
    }

    Users findByUsersId(String id);

}
