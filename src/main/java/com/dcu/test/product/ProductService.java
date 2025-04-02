package com.dcu.test.product;

import com.dcu.test.pakage.FileService;
import com.dcu.test.pakage.ProductCreateDTO;
import com.dcu.test.pakage.ProductDTO;
import com.dcu.test.pakage.ProductUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileService fileService;

    // 모든 상품 조회 메서드 (페이징 적용)
    public Page<ProductDTO> productFindAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImage());
            productDTO.setTitle(product.getTitle());
            productDTO.setCategory(product.getCategory());
            productDTO.setCompany(product.getCompany());
            productDTO.setProductCondition(product.getProductCondition());
            productDTO.setPrice(product.getPrice());
            productDTO.setRelease_date(product.getRelease_date());
            productDTO.setTrade_method(product.getTrade_method());
            productDTO.setDescription(product.getDescription());
            return productDTO;
        });
    }

    // 검색 기능 추가 (페이징 적용)
    public Page<ProductDTO> productSearch(String keyword, Pageable pageable) {
        Page<Product> products = (keyword != null && !keyword.trim().isEmpty())
                ? productRepository.findByTitleContainingIgnoreCase(keyword, pageable)
                : productRepository.findAll(pageable);

        return products.map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImage());
            productDTO.setTitle(product.getTitle());
            productDTO.setCategory(product.getCategory());
            productDTO.setCompany(product.getCompany());
            productDTO.setProductCondition(product.getProductCondition());
            productDTO.setPrice(product.getPrice());
            productDTO.setRelease_date(product.getRelease_date());
            productDTO.setTrade_method(product.getTrade_method());
            productDTO.setDescription(product.getDescription());
            return productDTO;
        });
    }

    public void productUpdate(ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(productUpdateDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        String imagePath = productUpdateDTO.getOriginalImage();
        if (productUpdateDTO.getImage() != null && !productUpdateDTO.getImage().isEmpty()) {
            try {
                fileService.fileDelete(String.valueOf(Paths.get(productUpdateDTO.getOriginalImage()).getFileName()));
                imagePath = fileService.imageSave(productUpdateDTO.getImage());
            } catch (IOException e) {
                throw new RuntimeException("이미지 처리 중 오류 발생", e);
            }
        }

        product.setImage(imagePath);
        product.setTitle(productUpdateDTO.getTitle());
        product.setPrice(productUpdateDTO.getPrice());
        product.setCompany(productUpdateDTO.getCompany());
        product.setRelease_date(productUpdateDTO.getRelease_date());
        product.setCategory(productUpdateDTO.getCategory());
        product.setTrade_method(productUpdateDTO.getTrade_method());
        product.setDescription(productUpdateDTO.getDescription());
        product.setProductCondition(productUpdateDTO.getProductCondition());

        productRepository.save(product);
    }

    // 상품 저장 메서드
    public void productSave(ProductCreateDTO productCreateDTO, String imagePath) {
        Product product = new Product();
        product.setImage(imagePath);
        product.setTitle(productCreateDTO.getTitle());
        product.setCategory(productCreateDTO.getCategory());
        product.setCompany(productCreateDTO.getCompany());
        product.setProductCondition(productCreateDTO.getProductCondition());
        product.setPrice(productCreateDTO.getPrice());
        product.setRelease_date(productCreateDTO.getRelease_date());
        product.setTrade_method(productCreateDTO.getTrade_method());
        product.setDescription(productCreateDTO.getDescription());

        productRepository.save(product);
    }

    // 상품 ID로 조회 메서드
    public Optional<ProductDTO> productFindById(Long id) {
        return productRepository.findById(id).map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImage());
            productDTO.setTitle(product.getTitle());
            productDTO.setCategory(product.getCategory());
            productDTO.setCompany(product.getCompany());
            productDTO.setProductCondition(product.getProductCondition());
            productDTO.setPrice(product.getPrice());
            productDTO.setRelease_date(product.getRelease_date());
            productDTO.setTrade_method(product.getTrade_method());
            productDTO.setDescription(product.getDescription());
            return productDTO;
        });
    }

    // 상품 삭제 메서드
    public void productDelete(Long id) {
        productRepository.deleteById(id);
    }
}
