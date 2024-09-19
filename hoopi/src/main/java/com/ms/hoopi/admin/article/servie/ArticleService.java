package com.ms.hoopi.admin.article.servie;

import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    ResponseEntity<String> addArticle(List<MultipartFile> imgs, ProductRequestDto product, ArticleRequestDto article) throws IOException;
}
