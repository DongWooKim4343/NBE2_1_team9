package team9.gccoffee.domain.product.service;


import org.springframework.data.domain.Page;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;

public interface ProductService {
    //product 전체 상품 조회
    //product 특정 상품 조회 id
    //product 생성
    //product 수정
    //product 삭제

    // 개별 상품 조회
    public ProductResponse getProductById(Long productId);
    // 전체 상품 조회
    public Page<Product> getAllProducts(ProductRequest productPageRequest);
    // 상품 생성
    public ProductResponse createProduct(ProductRequest productrequest);
    // 상품 수정
    public ProductResponse updateProduct(ProductRequest productrequest);
    // 상품 삭제
    public void deleteProduct(Long productId); //product service interface
}


