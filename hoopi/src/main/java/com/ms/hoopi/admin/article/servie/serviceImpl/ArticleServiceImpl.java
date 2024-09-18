package com.ms.hoopi.admin.article.servie.serviceImpl;

import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import com.ms.hoopi.admin.article.servie.ArticleService;
import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.*;
import com.ms.hoopi.repository.*;
import com.ms.hoopi.util.CommonUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImgRepository articleImgRepository;
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final UserRepository userRepository;
    private final CommonUtil commonUtil;
    private final BoardRepository boardRepository;

    // 게시글 저장
    @Override
    public ResponseEntity<String> addArticle(List<MultipartFile> imgs, ProductRequestDto product, ArticleRequestDto article) throws IOException {
        // 저장되어야할 user 정보 가져오기
        User user = userRepository.findById(article.getId())
                                    .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_USER));
        // 저장되어야할 board 정보 가져오기
        Board board = boardRepository.findBoardByBoardCode(article.getBoardCode())
                                        .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_BOARD));
        // product 정보 존재할때만 실행
        boolean flag = false;
        Product productEntity = null;
        if(!product.getName().isEmpty() && product.getPrice() != 0 && product.getStock() != 0){
            productEntity = addProduct(product, imgs);
            flag = true;
        }

        // article 정보 저장
        String articleCode = commonUtil.createCode();
        Article articleEntity = Article.builder()
                                .articleCode(articleCode)
                                .articleTitle(article.getArticleTitle())
                                .code(user)
                                .boardContent(article.getBoardContent())
                                .productCode(productEntity)
                                .boardCode(board)
                                .build();
        articleRepository.save(articleEntity);

        if(!flag){
            List<ArticleImg> articleImgs = new ArrayList<>();
            for(MultipartFile img : imgs){
                String articleImgCode = commonUtil.createCode();
                ArticleImg articleImgEntity = ArticleImg.builder()
                                                        .articleImgCode(articleImgCode)
                                                        .articleCode(articleEntity)
                                                        .imgType(img.getContentType())
                                                        .imgData(img.getBytes())
                                                        .boardCode(articleEntity.getBoardCode())
                                                        .fileName(img.getOriginalFilename())
                                                        .code(user)
                                                        .build();
                articleImgs.add(articleImgEntity);
            }
            articleImgRepository.saveAll(articleImgs);
        }
        return ResponseEntity.ok(Constants.ARTICLE_SUCCESS);
    }

    // product정보 저장
    public Product addProduct(ProductRequestDto product, List<MultipartFile> imgs) throws IOException {
        // product 정보 저장
        String productCode = commonUtil.createCode();
        Product productEntity = Product.builder()
                                        .productCode(productCode)
                                        .name(product.getName())
                                        .price(product.getPrice())
                                        .stock(product.getStock())
                                        .build();
        productRepository.save(productEntity);

        //productImg 정보 저장
        String productImgCode = commonUtil.createCode();
        List<ProductImg> productImgs = new ArrayList<>();
        for(MultipartFile img : imgs) {
            ProductImg productImg = ProductImg.builder()
                    .productImgCode(productImgCode)
                    .productCode(productEntity)
                    .imgData(img.getBytes())
                    .fileName(img.getOriginalFilename())
                    .contentType(img.getContentType())
                    .build();
            productImgs.add(productImg);
        }
        productImgRepository.saveAll(productImgs);

        return productEntity;
    }
}
