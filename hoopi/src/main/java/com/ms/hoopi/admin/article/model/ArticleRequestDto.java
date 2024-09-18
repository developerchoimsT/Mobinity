package com.ms.hoopi.admin.article.model;

import com.ms.hoopi.model.entity.Board;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleRequestDto {
    private String id;
    private String articleTitle;
    private String boardCode;
    private String boardContent;
}
