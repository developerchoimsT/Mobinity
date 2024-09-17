package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "super_id")
    private Integer superId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_code", nullable = false)
    private Board boardCode;

    @Size(max = 20)
    @NotNull
    @Column(name = "category_id", nullable = false, length = 20)
    private String categoryId;

    @OneToMany(mappedBy = "category")
    private Set<Article> articles = new LinkedHashSet<>();

}