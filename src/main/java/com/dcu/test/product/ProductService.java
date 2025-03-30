package com.dcu.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 상품 조회 메서드
    List<Product> productFindAll(){
        return productRepository.findAll();
    }

    //상품 목록 메서드
    public void productSave(Product product){
        productRepository.save(product);
    }

    public Optional<Product> productFindById(Long id){
        return productRepository.findById(id);
    }

    public void productDelete(Long id){
        productRepository.deleteById(id);
    }

}
