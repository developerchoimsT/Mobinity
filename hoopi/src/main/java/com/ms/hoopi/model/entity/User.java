package com.ms.hoopi.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "id", nullable = false, length = 25, unique = true)
    private String id;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "name", nullable = false, length = 20)
    @Pattern(regexp = "^[가-힣]{2,6}$", message = "이름은 한글만 허용됩니다.")
    private String name;

    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "^[0-9]{10,11}$", message = "10~11자리 수를 입력해주세요.")
    private String phone;

    @Column(name = "email", nullable = false, length = 150)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "유효한 이메일 주소를 입력하세요")
    private String email;

    @ColumnDefault("'N'")
    @Column(name = "quit_yn", length = 1)
    private String quitYn = "N";

    @Column(name = "join_date")
    private LocalDateTime joinDate = LocalDateTime.now();

    @Column(name = "quit_date")
    private LocalDateTime quitDate;

    @ColumnDefault("'user'")
    @Column(name = "role", length = 20)
    private String role = "user";

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Address> addresses = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Article> articles = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<ArticleImg> articleImgs = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Cart> carts = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Delivery> deliveries = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Payment> payments = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "code")
    private Set<Reply> replies = new LinkedHashSet<>();

    @Builder
    public User (String code, String id, String pwd, String name, String email, String phone, LocalDate birth) {
        this.code = code;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
    }

    @PrePersist
    @PreUpdate
    private void validateUser() {
        validateName();
        validatePhone();
        validateEmail();
    }

    private void validatePhone() {
        if (phone == null || !phone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("전화번호는 10자리 또는 11자리의 숫자만 허용됩니다.");
        }
    }

    private void validateEmail() {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("유효한 이메일 주소를 입력하세요");
        }
    }

    private void validateName() {
        if(name == null || !name.matches("^[가-힣]{2,6}$")) {
            throw new IllegalArgumentException("이름은 한글로 2~6자리 입력 가능합니다.");
        }
    }

}