package com.ms.hoopi.join.controller;

import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.JoinService;
import com.ms.hoopi.join.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoopi")
public class JoinController {

    @Autowired
    private final JoinService joinService;
    public JoinController (JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> userJoin(@RequestBody UsersDto users) {
        try {
            System.out.println("users::::" + users);
            joinService.joinUser(users);
            return ResponseEntity.ok("User successfully registered");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration");
        }
    }
}
