package com.dcu.test.product;

import com.dcu.test.pakage.FileService;
import com.dcu.test.pakage.ProductCreateDTO;
import com.dcu.test.pakage.ProductDTO;
import com.dcu.test.pakage.ProductUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    // 상품 목록 페이지 (한 페이지당 8개의 상품)
    @GetMapping({"/", "/productList"})
    public String productList(Model model, @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "8") int size) { // size를 8로 설정
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.productFindAll(pageable);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "productList";
    }

    @GetMapping("/productRegister")
    public String productRegister() {
        return "productRegistration";
    }

    @PostMapping("/productRegister")
    public String productRegister(@ModelAttribute ProductCreateDTO productCreateDTO) throws IOException {
        String imagePath = fileService.imageSave(productCreateDTO.getImage());
        productService.productSave(productCreateDTO, imagePath);
        return "redirect:/productList";
    }

    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<ProductDTO> product = productService.productFindById(id);
        product.ifPresent(dto -> model.addAttribute("product", dto));
        return product.isPresent() ? "productDetail" : "redirect:/productList";
    }

    @GetMapping("/productEdit/{id}")
    public String productEdit(@PathVariable Long id, Model model) {
        Optional<ProductDTO> product = productService.productFindById(id);
        product.ifPresent(dto -> model.addAttribute("product", dto));
        return product.isPresent() ? "productEdit" : "redirect:/productList";
    }

    @PostMapping("/productEdit")
    public String productEdit(@ModelAttribute ProductUpdateDTO productUpdateDTO) {
        productService.productUpdate(productUpdateDTO);
        return "redirect:/productDetail/" + productUpdateDTO.getId();
    }

    @PostMapping("/productDelete")
    public String productDelete(@RequestParam("id") Long id) {
        productService.productDelete(id);
        return "redirect:/productList";
    }

    @GetMapping("/productSearch")
    public String productSearch(@RequestParam String keyword, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size) { // size를 8로 설정
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.productSearch(keyword, pageable);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "productList";
    }
}
