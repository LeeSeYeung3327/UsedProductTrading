package com.dcu.test.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 회원 ID (PK)

    @Column(nullable = false, unique = true)
    private String email;  // 이메일 (로그인 ID)

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Column(nullable = false)
    private String name;  // 이름

    private String address;  // 주소 (null 가능)

    private String profileImage;  // 프로필 사진 URL (null 가능)

    @Column(nullable = false)
    private LocalDate joinDate;  // 가입일

    @Column(nullable = false)
    private String role = "ROLE_USER";  // 기본 역할 (사용자)
}
