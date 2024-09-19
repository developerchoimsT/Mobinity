package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.OrderDetail;
import com.ms.hoopi.model.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepoisitory extends JpaRepository<OrderDetail, OrderDetailId> {

    @Query("SELECT od FROM OrderDetail od WHERE od.orderCode = :orderCode")
    List<OrderDetail> findByOrderCode(String orderCode);
}
