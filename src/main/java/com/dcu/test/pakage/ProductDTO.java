package com.dcu.test.pakage;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String image;
    private String title;
    private String category;
    private Integer price;
    private String company;
    private String productCondition;
    private LocalDate release_date;
    private String trade_method;
    private String description;
}
