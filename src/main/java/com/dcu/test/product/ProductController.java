package com.dcu.test.product;

import com.dcu.test.member.Member;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import com.dcu.test.pakage.FIleService;


import java.io.IOException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FIleService fIleService;

    @GetMapping({"/", "/productList"})
    String productList(Model model) {
        List<Product> products = productService.productFindAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/productRegister")
    String productRegister() {
        return "productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(MultipartFile image, String title, Integer price, String company,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate release_date) throws IOException {

        // Save the image using FileService
        String imagePath = fIleService.imageSave(image);

        // Create the product and save it using ProductService
        Product product = new Product();
        product.setImage(imagePath);
        product.setTitle(title);
        product.setPrice(price);
        product.setCompany(company);
        product.setRelease_date(release_date);

        productService.productSave(product);

        // After saving, redirect to the product list page
        return "redirect:/productList";
    }

    @GetMapping("/productDetail/{id}")
    String productDetail(@PathVariable Long id, Model model) {
        // 에러 방지 - Optional 사용
        Optional<Product> product = productService.productFindById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDetail";
        } else {
            return "redirect:/productList";
        }
    }

    @GetMapping("/productEdit/{id}")
    String productEdit(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.productFindById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productEdit";
        } else {
            return "redirect:/productList";
        }
    }

    @PostMapping("/productEdit")
    String productEdit(@ModelAttribute Product product) {
        productService.productSave(product);
        return "redirect:/productList";
    }

    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute Product product) {
        productService.productDelete(product.getId());
        return "redirect:/productList";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam(required = false) String query, Model model) {
        // 검색어가 없으면 모든 제품을 가져옴
        List<Product> products;
        if (query == null || query.trim().isEmpty()) {
            products = productService.productFindAll(); // 검색어가 없으면 모든 제품을 불러옴
        } else {
            products = productService.searchProducts(query); // 검색어로 필터링한 제품을 불러옴
        }

        model.addAttribute("products", products);
        model.addAttribute("query", query);  // 검색어를 전달하여 검색창에 다시 표시
        return "productList"; // 'productList' 뷰를 반환
    }
}
