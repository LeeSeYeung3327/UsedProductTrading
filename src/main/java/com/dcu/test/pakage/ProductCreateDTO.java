package com.dcu.test.pakage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class ProductCreateDTO {
    private Long id;
    private MultipartFile image;
    private String title;
    private String category;
    private Integer price;
    private String company;
    private String productCondition;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate release_date;
    private String trade_method;
    private String description;
}
