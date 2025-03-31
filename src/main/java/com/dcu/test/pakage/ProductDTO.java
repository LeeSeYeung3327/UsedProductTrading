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
    private Integer price;
    private String company;
    private LocalDate manufactureDate;
    private LocalDateTime createAt;
}
