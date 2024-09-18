package com.ms.hoopi.admin.user.controller;

import com.ms.hoopi.admin.user.model.dto.UserOrderSelectDto;
import com.ms.hoopi.admin.user.model.dto.UserSelectResponseDto;
import com.ms.hoopi.admin.user.model.dto.UserUpdateRequestDto;
import com.ms.hoopi.admin.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hoopi/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // user 정보 가져오기
    @GetMapping("/user")
    public Page<UserSelectResponseDto> userSelect(@RequestParam(defaultValue = "") String searchCate,
                                                  @RequestParam(defaultValue = "") String keyword,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("searchCate={}", searchCate);
        log.info("keyword={}", keyword);
        return adminUserService.userSelect(searchCate, keyword, page, size);
    }

    // user detail 정보 가져오기
    @GetMapping("/user-detail")
    public ResponseEntity<?> userDetailSelect(@RequestParam String code) {
        return adminUserService.userSelectDetail(code);
    }

    // user의 주문 정보 가져오기
    @GetMapping("/user-order")
    public Page<UserOrderSelectDto> userOrderSelect(@RequestParam String code,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return adminUserService.userOrder(code, page, size);
    }

    // user 정보 수정(탈퇴)
    @PutMapping("/user-quit")
    public ResponseEntity<String> userQuit(@RequestParam String id) {
        log.info("id={}", id);
        return adminUserService.userQuit(id);
    }

    // user 정보 수정
    @PutMapping("/user")
    public ResponseEntity<String> userUpdate(@RequestBody UserUpdateRequestDto user){
        return adminUserService.userUpdate(user);
    }

}
