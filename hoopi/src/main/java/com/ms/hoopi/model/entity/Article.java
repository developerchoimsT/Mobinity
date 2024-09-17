package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_code", nullable = false)
    private Board boardCode;

    @Column(name = "board_content", nullable = false, length = 2000)
    private String boardContent;

        @Column(name = "article_date")
    private LocalDateTime articleDate;

    @ColumnDefault("'N'")
    @Column(name = "delete_yn", nullable = false, length = 1)
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_code", nullable = false)
    private Product productCode;

    @OneToMany(mappedBy = "articleCode")
    private Set<ArticleImg> articleImgs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "articleCode")
    private Set<Reply> replies = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }
}