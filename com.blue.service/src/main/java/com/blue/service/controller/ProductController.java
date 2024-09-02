package com.blue.service.controller;

import com.blue.service.application.ProductService;
import com.blue.service.application.dtos.product.ProductReqDto;
import com.blue.service.application.dtos.product.ProductResDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/store")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 메뉴 전체 조회
    @GetMapping("/product/{storeId}")
    @ResponseBody
    public List<ProductResDto> getProducts(@PathVariable UUID storeId) {
        return productService.getProducts(storeId);
    }
    
    // 메뉴 검색
    @GetMapping("/product/search")
    public ResponseEntity<List<ProductResDto>> searchProduct(
            @Parameter(description = "UserName Header")
            @RequestHeader(name = "X-User-Name", required = false) String userName,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "isAsc") Boolean isAsc,
            @RequestParam(name = "orderBy") String orderBy,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "productName", required = false) String productName
    ) {
        return ResponseEntity.ok(productService.searchProduct(userName, page, limit, isAsc, orderBy, keyword, productName));
    }

    // 메뉴 상세 조회
    @GetMapping("/details/{productId}")
    @ResponseBody
    public ProductResDto getProductDetails(@PathVariable UUID productId) {
        return productService.getProductDetails(productId);
    }

    // 메뉴 추가
    @PostMapping("/{storeId}/product")
    @ResponseBody
    public ProductResDto createProduct(
            @PathVariable UUID storeId,
            @RequestBody ProductReqDto reqDto,
            @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return productService.createProduct(storeId, reqDto, userName);
    }

    // 메뉴 수정
    @PutMapping("/product/{productId}")
    @ResponseBody
    public ProductResDto updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductReqDto reqDto,
            @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return productService.updateProduct(productId, reqDto, userName);
    }

    // 메뉴 삭제
    @DeleteMapping("/{storeId}/{productId}")
    @ResponseBody
    public ResponseEntity<ProductResDto> deleteProduct(@PathVariable UUID productId, @PathVariable UUID storeId, @RequestHeader(name = "X-User-Name", required = false) String userName) {
        return productService.deleteProduct(productId,storeId, userName);
    }


}
