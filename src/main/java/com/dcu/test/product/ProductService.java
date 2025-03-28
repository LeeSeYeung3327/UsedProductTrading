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

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchDataFromExternalApi() {
        // API URL 수정 (올바른 엔드포인트로 변경)
        String url = "http://apis.data.go.kr/1360000/RoadWthrInfoService/getCctvStnRoadWthr?serviceKey=LoGbj2ildW8Dn%2Bqnz%2FmR7JdGzZYL925XtK4lEx%2B31m%2FdYC5aA1NSfrARfMCQauUatApV29yDTuJmauoo6G4B5w%3D%3D&numOfRows=10&pageNo=10&eqmtId=0500C00119&hhCode=00&dataType=JSON";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response);
        // 응답 본문 반환
        return response.getBody();
    }
}
