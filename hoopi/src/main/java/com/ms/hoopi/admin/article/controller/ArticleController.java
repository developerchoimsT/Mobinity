package com.ms.hoopi.admin.article.controller;

import com.ms.hoopi.admin.article.model.ArticleCreationRequestDto;
import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import com.ms.hoopi.admin.article.servie.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("hoopi/")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<String> addArticle(@RequestParam("imgs")List<MultipartFile> imgs
                                            , @RequestBody ArticleCreationRequestDto request) throws IOException {
        return articleService.addArticle(imgs, request.getProduct(), request.getArticle());
    }
}
