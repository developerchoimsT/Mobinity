package com.ms.hoopi.admin.article.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleCreationRequestDto {
    private ProductRequestDto product;
    private ArticleRequestDto article;
}
