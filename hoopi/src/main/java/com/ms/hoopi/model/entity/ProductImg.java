package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_img")
public class ProductImg {
    @Id
    @Column(name = "product_img_code", nullable = false)
    private String productImgCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_code", nullable = false)
    private Product productCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Size(max = 255)
    @Column(name = "img_path")
    private String imgPath;

    @Size(max = 500)
    @NotNull
    @Column(name = "img_key", nullable = false)
    private String imgKey;

    @Builder
    public ProductImg(String productImgCode, Product productCode, String imgPath, String fileName, String imgKey) {
        this.productImgCode = productImgCode;
        this.productCode = productCode;
        this.imgPath = imgPath;
        this.fileName = fileName;
        this.imgKey = imgKey;
    }
}