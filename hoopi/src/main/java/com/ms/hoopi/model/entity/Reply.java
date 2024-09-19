package com.ms.hoopi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @Column(name = "reply_code", nullable = false)
    private String replyCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_code", nullable = false)
    private Article articleCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_code", nullable = false)
    private Board boardCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @Column(name = "super_code", nullable = false)
    private String superCode;

    @Column(name = "reply_content", nullable = false, length = 1000)
    private String replyContent;

    @Column(name = "depth", nullable = false, length = 1)
    private String depth;

    @Column(name = "reply_date")
    private LocalDateTime replyDate;

    @Column(name = "delete_yn", nullable = false, length = 1)
    private String deleteYn;

}