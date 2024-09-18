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

    @NotNull
    @Column(name = "img_data", nullable = false)
    private byte[] imgData;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Size(max = 50)
    @NotNull
    @Column(name = "content_type", nullable = false, length = 50)
    private String contentType;

    @Builder
    public ProductImg(String productImgCode, Product productCode, byte[] imgData, String fileName, String contentType) {
        this.productImgCode = productImgCode;
        this.productCode = productCode;
        this.imgData = imgData;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}