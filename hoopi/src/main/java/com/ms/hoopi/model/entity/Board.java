package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_code", nullable = false)
    private String boardCode;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "depth", nullable = false, length = 1)
    private String depth;

    @Column(name = "super_id", nullable = false)
    private String superId;

    @Column(name = "board_id", nullable = false)
    private String boardId;

    @OneToMany(mappedBy = "boardCode")
    private Set<Article> articles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "boardCode")
    private Set<ArticleImg> articleImgs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "boardCode")
    private Set<Reply> replies = new LinkedHashSet<>();

}