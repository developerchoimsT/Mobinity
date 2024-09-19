package com.ms.hoopi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article")
public class Article {
    @Id
    @Column(name = "article_code", nullable = false)
    private String articleCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_code", nullable = false)
    private Board boardCode;

    @Column(name = "board_content", nullable = false, length = 2000)
    private String boardContent;

    @Column(name = "article_date")
    private LocalDateTime articleDate = LocalDateTime.now();

    @ColumnDefault("'N'")
    @Column(name = "delete_yn", nullable = false, length = 1)
    private String deleteYn = "N";

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product productCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "articleCode")
    private Set<ArticleImg> articleImgs = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "articleCode")
    private Set<Reply> replies = new LinkedHashSet<>();

    @Size(max = 100)
    @NotNull
    @Column(name = "article_title", nullable = false, length = 100)
    private String articleTitle;

    @Builder
    public Article(String articleCode, User code, Board boardCode, String boardContent, Product productCode, String articleTitle) {
        this.articleCode = articleCode;
        this.code = code;
        this.boardCode = boardCode;
        this.boardContent = boardContent;
        this.productCode = productCode;
        this.articleTitle = articleTitle;
    }
}