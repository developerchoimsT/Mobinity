package com.ms.hoopi.product.controller;

import com.ms.hoopi.model.entity.Product;
import com.ms.hoopi.product.model.ProductDetailResponseDto;
import com.ms.hoopi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("hoopi")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product-new")
    public List<ProductDetailResponseDto> getProductsNew() {
        return productService.getProductsNew();
    }

    @GetMapping("/product-popular")
    public List<ProductDetailResponseDto> getProductsPopular() {
        return productService.getProductsPopular();
    }
}
