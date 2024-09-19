package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article_img")
public class ArticleImg {
    @Id
    @Column(name = "article_img_code", nullable = false)
    private String articleImgCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_code", nullable = false)
    private Article articleCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_code", nullable = false)
    private Board boardCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Size(max = 255)
    @NotNull
    @Column(name = "img_path", nullable = false)
    private String imgPath;

    @Size(max = 500)
    @NotNull
    @Column(name = "img_key", nullable = false)
    private String imgKey;


    @Builder
    public ArticleImg(String articleImgCode, Article articleCode, User code, Board boardCode, String imgPath, String fileName, String imgKey) {
        this.articleImgCode = articleImgCode;
        this.articleCode = articleCode;
        this.code = code;
        this.boardCode = boardCode;
        this.imgPath = imgPath;
        this.fileName = fileName;
        this.imgKey = imgKey;
    }

}