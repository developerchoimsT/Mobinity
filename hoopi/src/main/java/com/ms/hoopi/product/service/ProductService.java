package com.ms.hoopi.product.service;

import com.ms.hoopi.model.entity.Product;
import com.ms.hoopi.product.model.ProductDetailResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductDetailResponseDto> getProductsNew();

    List<ProductDetailResponseDto> getProductsPopular();
}
