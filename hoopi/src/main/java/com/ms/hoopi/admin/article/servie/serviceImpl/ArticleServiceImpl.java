package com.ms.hoopi.admin.article.servie.serviceImpl;

import com.ms.hoopi.admin.article.model.ArticleRequestDto;
import com.ms.hoopi.admin.article.model.ProductRequestDto;
import com.ms.hoopi.admin.article.servie.ArticleService;
import com.ms.hoopi.common.service.FileUploadService;
import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.*;
import com.ms.hoopi.repository.*;
import com.ms.hoopi.common.util.CommonUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    private final FileUploadService fileUploadService;

    // 게시글 저장
    @Override
    public ResponseEntity<String> addArticle(List<MultipartFile> imgs, ProductRequestDto product, ArticleRequestDto article) throws IOException {
        try{
            // 저장되어야할 user 정보 가져오기
            User user = userRepository.findById(article.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_USER));
            log.info("user:{}",user);
            // 저장되어야할 board 정보 가져오기
            Board board = boardRepository.findBoardByBoardCode(article.getBoardCode())
                    .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_BOARD));
            log.info("board:{}",board);
            Article articleEntity;
            // product 정보 존재할때만 실행
            boolean flag = false;
            if(!product.getName().isEmpty() && product.getPrice() != 0 && product.getStock() != 0){
                Product productEntity = addProduct(product, imgs);
                flag = true;
                // article 정보 저장
                String articleCode = commonUtil.createCode();
                articleEntity = Article.builder()
                        .articleCode(articleCode)
                        .articleTitle(article.getArticleTitle())
                        .code(user)
                        .boardContent(article.getBoardContent())
                        .productCode(productEntity)
                        .boardCode(board)
                        .build();
                articleRepository.save(articleEntity);
            } else {
                // productEntity없을 때 article 정보 저장
                String articleCode = commonUtil.createCode();
                articleEntity = Article.builder()
                        .articleCode(articleCode)
                        .articleTitle(article.getArticleTitle())
                        .code(user)
                        .boardContent(article.getBoardContent())
                        .boardCode(board)
                        .build();
                articleRepository.save(articleEntity);
            }


            log.info("article:{}",articleEntity);

            List<String> keys = new ArrayList<>();
            if(!flag){
                List<ArticleImg> articleImgs = new ArrayList<>();
                for(MultipartFile img : imgs){
                    String articleImgCode = commonUtil.createCode();
                    String key = commonUtil.createS3Key("article", img.getOriginalFilename());
                    ArticleImg articleImgEntity = ArticleImg.builder()
                            .articleImgCode(articleImgCode)
                            .articleCode(articleEntity)
                            .boardCode(articleEntity.getBoardCode())
                            .fileName(img.getOriginalFilename())
                            .imgPath("article")
                            .imgKey(key)
                            .code(user)
                            .build();
                    keys.add(key);
                    articleImgs.add(articleImgEntity);
                }
                articleImgRepository.saveAll(articleImgs);
                fileUploadService.uploadFile(imgs, keys);
                log.info("articleImgs:{}",articleImgs);
            }
            return ResponseEntity.ok(Constants.ARTICLE_SUCCESS);
        } catch (Exception e) {
            log.error(Constants.ARTICLE_FAIL, e);
            throw e;
        }
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
        log.info("product:{}",productEntity);

        //productImg 정보 저장
        List<ProductImg> productImgs = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for(MultipartFile img : imgs) {
            String productImgCode = commonUtil.createCode();
            String key = commonUtil.createS3Key("product", img.getOriginalFilename());
            ProductImg productImg = ProductImg.builder()
                    .productImgCode(productImgCode)
                    .productCode(productEntity)
                    .fileName(img.getOriginalFilename())
                    .imgPath("product")
                    .imgKey(key)
                    .build();
            productImgs.add(productImg);
            keys.add(key);
        }
        productImgRepository.saveAll(productImgs);
        fileUploadService.uploadFile(imgs, keys);
        log.info("productImgs:{}",productImgs);

        return productEntity;
    }
}
