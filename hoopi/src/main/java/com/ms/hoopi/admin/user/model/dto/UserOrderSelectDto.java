package com.ms.hoopi.admin.user.model.dto;

import com.ms.hoopi.model.entity.OrderDetail;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserOrderSelectDto {
    private String orderCode;

    private LocalDateTime orderDate;

    private List<UserOrderDetailSelectDto> orderDetails;

}
