package com.ms.hoopi.admin.user.model.dto;

import com.ms.hoopi.model.entity.OrderDetailId;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserOrderDetailSelectDto {
    private OrderDetailId orderDetailId;
    private Long quantity;
    private Long orderAmount;
    private BigDecimal totalPrice;
}
