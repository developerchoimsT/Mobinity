package com.ms.hoopi.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    private Integer id;
    private Integer depth;
    private String name;
    private Integer superId;
    private String categoryId;
}
