package com.dcu.test.pakage;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProductUpdateDTO {

 @NotNull(message = "ID는 필수 값입니다.")
 private Long id;

 private MultipartFile image;

 @NotNull(message = "이미지 경로가 필요합니다.")
 private String originalImage = "";

 @NotNull(message = "제목을 입력해주세요.")
 private String title;

 @NotNull(message = "카테고리를 선택해주세요.")
 private String category;

 @NotNull(message = "가격을 입력해주세요.")
 private Integer price;

 private String company;

 @NotNull(message = "상품 상태를 선택해주세요.")
 private String productCondition;

 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
 private LocalDate release_date;

 @NotNull(message = "거래 방식을 선택해주세요.")
 private String trade_method;

 @NotNull(message = "설명을 입력해주세요.")
 private String description;
}
