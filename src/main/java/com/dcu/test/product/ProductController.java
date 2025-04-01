package com.dcu.test.product;

import com.dcu.test.pakage.FileService;
import com.dcu.test.pakage.ProductCreateDTO;
import com.dcu.test.pakage.ProductDTO;
import com.dcu.test.pakage.ProductUpdateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    @GetMapping({"/", "/productList"})
    public String productList(Model model) {
        List<ProductDTO> products = productService.productFindAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/productRegister")
    public String productRegister() {
        return "productRegistration";
    }

    @PostMapping("/productRegister")
    public String productRegister(@ModelAttribute ProductCreateDTO productCreateDTO) throws IOException {
        // MultipartFile을 사용하여 이미지 저장
        String imagePath = fileService.imageSave(productCreateDTO.getImage());

        // ProductCreateDTO와 이미지 경로를 사용해 저장
        productService.productSave(productCreateDTO, imagePath);

        return "redirect:/productList";
    }

    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<ProductDTO> product = productService.productFindById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDetail";
        } else {
            return "redirect:/productList";
        }
    }

    @GetMapping("/productEdit/{id}")
    public String productEdit(@PathVariable Long id, Model model) {
        Optional<ProductDTO> product = productService.productFindById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productEdit";
        } else {
            return "redirect:/productList";
        }
    }

    @PostMapping("/productEdit")
    public String productEdit(@ModelAttribute ProductUpdateDTO productUpdateDTO){
        productService.productUpdate(productUpdateDTO);
        return "redirect:/productDetail/"+productUpdateDTO.getId();
    }

    @PostMapping("/productDelete")
    public String productDelete(@RequestParam("id") Long id) {
        productService.productDelete(id);
        return "redirect:/productList";
    }

    @GetMapping("/productSearch")
    String productSearch(String keyword, Model model){
        List<ProductDTO> products = productService.productSearch(keyword);
        model.addAttribute("products", products);
        return "productList";
    }

}
