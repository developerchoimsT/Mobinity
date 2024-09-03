package com.ms.hoopi.controller;

import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.service.JoinService;
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
    public ResponseEntity<String> userJoin(@RequestBody User user) throws Exception{
            if(joinService.joinUser(user)){
                return ResponseEntity.ok(Constants.JOIN_SUCCESS);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.JOIN_FAIL);
    }
}
