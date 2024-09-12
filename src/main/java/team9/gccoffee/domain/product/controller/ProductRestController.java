package team9.gccoffee.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductUpdateRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.service.ProductService;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Log4j2
public class ProductRestController {

    private final ProductService productService;

    //등록
    @Operation(
            summary = "상품 등록"
            , description = "상품을 등록하는 API.")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Validated @RequestBody ProductRequest productRequest) {


        return ResponseEntity.ok(productService.createProduct(productRequest));

    }

    //조회 ////////////
    //productId 로 상품 개별 조회
    @Operation(
            summary = "상품 조회"
            , description = "상품을 조회하는 API. productId 로 상품 개별 조회.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> read(
            @PathVariable("productId") Long productId) {

        return ResponseEntity.ok(productService.getProductById(productId));
    }


    //상품 전체 조회
    @Operation(
            summary = "상품 전체 조회"
            , description = "상품 전체를 조회하는 API")
    @GetMapping
    public ResponseEntity<Page<Product>> getProductList(
            @Validated MemberPageRequestDTO productPageRequestDTO) {

        return ResponseEntity.ok(productService.getAllProducts(productPageRequestDTO));

    }

    ////////////

    //수정 productId
    @Operation(
            summary = "상품 수정"
            , description = "상품을 수정하는 API, productId로 수정")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> modify(
            @Validated @RequestBody ProductUpdateRequest productUpdateRequest,
            @PathVariable("productId") Long productId ) {

        if( !productId.equals(productUpdateRequest.getProductId())) {
            throw new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return ResponseEntity.ok(productService.updateProduct(productUpdateRequest));
    }


    //삭제 productId
    @Operation(
            summary = "상품 삭제"
            , description = "상품을 삭제하는 API, productId로 삭제")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> remove(
            @PathVariable("productId") Long pid) {

        productService.deleteProduct(pid);

        return ResponseEntity.ok(Map.of("result", "success"));
    }
}












