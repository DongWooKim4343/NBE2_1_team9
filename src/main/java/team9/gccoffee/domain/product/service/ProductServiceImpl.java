package team9.gccoffee.domain.product.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductUpdateRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    //개별 상품 조회
    @Override
    public ProductResponse getProductById(Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        Product product = foundProduct.orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductResponse(product);
    }

    //전체 상품 조회
    @Override
    public Page<Product> getAllProducts(MemberPageRequestDTO productPageRequestDTO) {

        Sort sort = Sort.by("productId").descending();
        Pageable pageable = productPageRequestDTO.getPageable(sort);

        return productRepository.findAll(pageable);
    }

    // 상품 등록 //관리자만 가능
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Optional<Member> foundMember = memberRepository.findById(productRequest.getMemberId());
        Member member = foundMember.orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getMemberType() == MemberType.CUSTOMER) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_ADMIN);
        }

        try {
            Product product = productRequest.toEntity();
            productRepository.save(product);
            return new ProductResponse(product);
        } catch (Exception e) {
            throw new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_REGISTERED);
        }
    }

    // 상품 수정 //관리자만 가능
    @Override
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getProductId())
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<Member> foundMember = memberRepository.findById(productUpdateRequest.getMemberId());
        Member member = foundMember.orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));


        if (member.getMemberType() == MemberType.CUSTOMER) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_ADMIN);
        }

        try {
            product.changeProductName(productUpdateRequest.getProductName());
            product.changeCategory(productUpdateRequest.getCategory());
            product.changePrice(productUpdateRequest.getPrice());
            product.changeDescription(productUpdateRequest.getDescription());
            product.changeStockQuantity(productUpdateRequest.getStockQuantity());

            return new ProductResponse(product);
        } catch (Exception e){
            throw new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_MODIFIED);
        }
    }

    // 상품 삭제
    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND));

        try {
            productRepository.delete(product);
        } catch (Exception e) {
            throw new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_REMOVED);
        }
    }
}
