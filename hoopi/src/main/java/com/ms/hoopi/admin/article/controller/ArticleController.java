package com.ms.hoopi.admin.article.controller;

import com.ms.hoopi.admin.article.model.ArticleCreationRequestDto;
import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import com.ms.hoopi.admin.article.servie.ArticleService;
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
@RequestMapping("hoopi/")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<String> addArticle(@RequestParam("imgs")List<MultipartFile> imgs
                                            , @RequestParam("data") ArticleCreationRequestDto data) throws IOException {
        log.info("imgs:{}", imgs);
        log.info("data:{}", data);
        return articleService.addArticle(imgs, data.getProduct(), data.getArticle());
    }
}
