package com.ms.hoopi.admin.article.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.hoopi.admin.article.model.ArticleCreationRequestDto;
import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import com.ms.hoopi.admin.article.servie.ArticleService;
import com.ms.hoopi.model.entity.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("hoopi")
public class ArticleController {

    private final ObjectMapper objectMapper;

    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<String> addArticle(@RequestPart("file")List<MultipartFile> imgs
                                            , @RequestPart("data") String request) throws IOException {
        ArticleCreationRequestDto data = objectMapper.readValue(request, ArticleCreationRequestDto.class);
        log.info("data:{}", data);
        log.info("Number of images: {}", imgs);
        return articleService.addArticle(imgs, data.getProduct(), data.getArticle());
    }
}
