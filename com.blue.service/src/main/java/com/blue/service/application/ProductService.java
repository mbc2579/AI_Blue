package com.blue.service.application;

import com.blue.service.application.dtos.ProductReqDto;
import com.blue.service.application.dtos.ProductResDto;
import com.blue.service.application.dtos.StoreResDto;
import com.blue.service.domain.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Autowired
    private DescriptionGenerator descriptionGenerator;

    // 메뉴 전체 조회
    public List<ProductResDto> getProducts(UUID storeId) {
        List<Product> productList = productRepository.findAllByStore_StoreIdAndDeletedAtIsNullAndIsVisibleTrueOrderByCreatedAt(storeId);
        return productList.stream().map(ProductResDto::new).collect(Collectors.toList());
    }

    // 메뉴 상세 조회
    public ProductResDto getProductDetails(UUID productId) {
        Optional<Product> productOpt = productRepository.findByProductIdAndDeletedAtIsNull(productId);
        if (productOpt.isEmpty()) {
            throw new EntityNotFoundException("제품을 찾을 수 없거나 삭제되었습니다.");
        }
        return new ProductResDto(productOpt.get());
    }


    // 메뉴 추가
    public ProductResDto createProduct(UUID storeId, ProductReqDto reqDto, String userName) {
        Optional<Store> store = storeRepository.findById(storeId);

        if (!store.isPresent() || !store.get().getUserName().equals(userName)) {
            log.error("해당 가게는 존재하지 않거나 사장님만 작성 가능합니다.");
            return null;
        }

        // AI를 사용하여 설명 생성
        DescriptionGenerator descriptionGenerator = new DescriptionGenerator();
        String aiDescription = descriptionGenerator.generateDescription(reqDto.getProductName() + " 메뉴 설명해줘 50자 이내로");

        // AI 설명 로그 출력
        System.out.println("Generated AI Description: " + aiDescription);

        // Product 객체 생성
        Product product = new Product(reqDto, store.get(), aiDescription);

        // Product 저장
        productRepository.save(product);

        // 응답 반환
        return new ProductResDto(product);
    }


    // 메뉴 수정
    @Transactional
    public ProductResDto updateProduct(UUID productId, ProductReqDto reqDto, String userName) {
        Optional<Product> checkProduct = productRepository.findById(productId);
        if(!checkProduct.isPresent()) {
            log.error("해당 메뉴가 존재하지 않습니다.");
            return null;
        }

        Product product = checkProduct.get();
        String productUser = product.getStore().getUserName();

        if(!productUser.equals(userName)) {
            log.error("가게 사장님만 수정이 가능합니다.");
            return null;
        }

        // AI로부터 새로운 설명 생성
        String newDescription = descriptionGenerator.generateDescription(reqDto.getProductName() + " 메뉴 설명해줘 50자 이내로");
        product.update(reqDto);
        product.setDescription(newDescription);

        productRepository.save(product);

        log.info("메뉴 수정 완료");
        return new ProductResDto(product);
    }

    // 메뉴 삭제
    @Transactional
    public ResponseEntity<ProductResDto> deleteProduct(UUID productId, UUID storeId, String userName) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if(!productOpt.isPresent() || !storeOpt.get().getUserName().equals(userName)) {
            return ResponseEntity.status(400).body(toResponseDto(productOpt.get()));
        }

        Product product = productOpt.get();

        product.setDeleted(LocalDateTime.now(), userName);
        productRepository.save(product);

        return ResponseEntity.status(200).body(new ProductResDto(product));
    }

    private ProductResDto toResponseDto(Product product) {
        return new ProductResDto(
        );
    }
}
