package com.ms.hoopi.admin.user.service.serviceImpl;

import com.ms.hoopi.admin.user.model.dto.*;
import com.ms.hoopi.admin.user.service.AdminUserService;
import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.Order;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.repository.OrderDetailRepoisitory;
import com.ms.hoopi.repository.OrderRepository;
import com.ms.hoopi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    //user정보 가져오기
    @Override
    public Page<UserSelectResponseDto> userSelect(String searchCate, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> user;

        // keyword가 없으면 전체 유저 반환
        if(keyword == null || keyword.isEmpty()){
            user = userRepository.findAll(pageable);
        }
        //keyword가 있을 경우
         user = switch (searchCate) {
            case "id" -> userRepository.searchAllById(keyword, pageable);
            case "name" -> userRepository.searchAllByName(keyword, pageable);
            case "email" -> userRepository.searchAllByEmail(keyword, pageable);
            case "phone" -> userRepository.searchAllByPhone(keyword, pageable);
            default -> null;
        };

        if (user == null) {
            return null;
        }

        Page<UserSelectResponseDto> userPage = user.map(u -> UserSelectResponseDto.builder()
                                                                                .code(u.getCode())
                                                                                .userId(u.getId())
                                                                                .userName(u.getName())
                                                                                .phone(u.getPhone())
                                                                                .email(u.getEmail())
                                                                                .build());

        return userPage;
    }

    // user detail 정보 가져오기
    @Override
    public ResponseEntity<?> userSelectDetail(String code) {
        User user = userRepository.findByCode(code)
                                .orElseThrow(()-> new EntityNotFoundException(Constants.NONE_USER));
        try{
            //addressDetail완성하기
            List<AddressSelectDto> addressDetail = user.getAddresses().stream().map(address -> AddressSelectDto.builder()
                                                                                                                .addressCode(address.getAddressCode())
                                                                                                                .address(address.getAddress())
                                                                                                                .main(address.getMain())
                                                                                                                .build())
                                                                                .toList();
            //uesrDetail완성하기
            UserDetailSelectResponseDto userDetail = UserDetailSelectResponseDto.builder()
                                                                                .code(user.getCode())
                                                                                .id(user.getId())
                                                                                .name(user.getName())
                                                                                .phone(user.getPhone())
                                                                                .email(user.getEmail())
                                                                                .birth(user.getBirth())
                                                                                .joinDate(user.getJoinDate())
                                                                                .quitDate(user.getQuitDate())
                                                                                .quitYn(user.getQuitYn())
                                                                                .addressDto(addressDetail)
                                                                                .build();
            return ResponseEntity.ok(userDetail);

        } catch (Exception e){
            log.error(Constants.USER_DETAIL_NOT_FOUND, e);
            return ResponseEntity.badRequest().body(Constants.USER_DETAIL_NOT_FOUND);
        }
    }

    // user의 주문 정보 가져오기
    @Override
    public Page<UserOrderSelectDto> userOrder(String code, int page, int size) {
        // user 정보 불러오기
        User user = userRepository.findByCode(code)
                                    .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_USER));
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> order = orderRepository.findAllByUserCode(user, pageable);
        try{
            if(order == null || order.isEmpty()){
                return null;
            }
            // orderPage 완성시키기
            Page<UserOrderSelectDto> orderPage = order.map(o -> UserOrderSelectDto.builder()
                                                                                    .orderCode(o.getOrderCode())
                                                                                    .orderDate(o.getOrderDate())
                                                                                    .orderDetails(
                                                                                                    o.getOrderDetails().stream()
                                                                                                                        .map(od -> UserOrderDetailSelectDto.builder()
                                                                                                                                    .orderDetailId(od.getId())
                                                                                                                                    .quantity(od.getQuantity())
                                                                                                                                    .orderAmount(od.getOrderAmount())
                                                                                                                                    .totalPrice(od.getTotalPrice())
                                                                                                                                    .build())
                                                                                                    .toList())
                                                        .build());
            return orderPage;

        } catch (Exception e){
            log.error(Constants.USER_ORDER_NOT_FOUND, e);
            return null;
        }
    }

    // 회원 탈퇴 처리
    @Override
    public ResponseEntity<String> userQuit(String id) {
        try{
            userRepository.updateById(id);
            return ResponseEntity.ok(Constants.USER_QUIT_SUCCESS);

        } catch (Exception e){
            log.error(Constants.USER_QUIT_FAIL, e);
            return ResponseEntity.badRequest().body(Constants.USER_QUIT_FAIL);
        }
    }

    // 회원 수정 처리
    @Override
    public ResponseEntity<String> userUpdate(UserUpdateRequestDto user) {
        try{
            // 기존 데이터 조회
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Constants.REFRESH_ID_NOT_FOUND));

            // 데이터 업데이트
            User newUser = User.builder()
                    .code(existingUser.getCode())
                    .id(existingUser.getId())
                    .pwd(existingUser.getPwd())
                    .name(user.getName())
                    .birth(existingUser.getBirth())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .build();

            // 엔티티 저장
            userRepository.save(newUser);

            // 응답 반환
            return ResponseEntity.ok(Constants.USER_UPDATE_SUCCESS);
        } catch (Exception e){
            log.error(Constants.USER_UPDATE_FAIL, e);
            return ResponseEntity.badRequest().body(Constants.USER_UPDATE_FAIL);
        }
    }


}
