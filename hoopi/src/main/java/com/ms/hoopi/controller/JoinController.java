package com.ms.hoopi.controller;

import com.ms.hoopi.service.JoinService;
import com.ms.hoopi.model.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hoopi")
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
