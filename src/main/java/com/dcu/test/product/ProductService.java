package com.dcu.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 상품 조회 메서드
    public List<Product> productFindAll() {
        return productRepository.findAll();
    }

    // 검색 기능 추가
    public List<Product> searchProducts(String query) {
        return productRepository.findByTitleContainingOrCompanyContaining(query, query);
    }

    // 상품 저장 메서드
    public void productSave(Product product) {
        productRepository.save(product);
    }

    // 상품 ID로 조회 메서드
    public Optional<Product> productFindById(Long id) {
        return productRepository.findById(id);
    }

    // 상품 삭제 메서드
    public void productDelete(Long id) {
        productRepository.deleteById(id);
    }
}
