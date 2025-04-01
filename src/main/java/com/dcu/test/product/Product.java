package com.dcu.test.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import com.dcu.test.member.Member;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 상품 ID (자동 증가)

    @Column(columnDefinition = "TEXT")
    private String image;  // 물품 사진 URL

    @Column(nullable = false, unique = true)
    private String title;  // 물품명

    @Column(nullable = false)
    private String category;  // 카테고리 (예: 디지털/IT, 가구/인테리어)

    @Column(length = 100)
    private String company;  // 브랜드/제조사

    @Column(nullable = false)
    private String productCondition;  // 물품 상태 (예: 새상품, 거의 새것, 상태 좋음, 사용감 있음, 상태 안좋음)

    @Column(nullable = false)
    private Integer price;  // 판매 가격

    private LocalDate release_date;  // 구매 시기

    @Column(nullable = false)
    private String trade_method;  // 거래 방식 (예: 직거래, 택배거래)

    @Column(columnDefinition = "TEXT")
    private String description;  // 물품 설명 (사용감, 기능 이상 여부 등)
}
