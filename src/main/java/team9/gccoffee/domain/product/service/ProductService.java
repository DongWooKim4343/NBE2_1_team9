package team9.gccoffee.domain.product.service;

import org.springframework.data.domain.Page;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductUpdateRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;

public interface ProductService {
    // 개별 상품 조회
    public ProductResponse getProductById(Long productId);
    // 전체 상품 조회
    public Page<Product> getAllProducts(MemberPageRequestDTO productPageRequestDTO);

    // 상품 생성
    public ProductResponse createProduct(ProductRequest productrequest);
    // 상품 수정
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest);
    // 상품 삭제
    public void deleteProduct(Long productId);


}
