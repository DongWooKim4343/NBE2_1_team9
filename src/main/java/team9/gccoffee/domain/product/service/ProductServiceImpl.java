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
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    //개별 상품 조회
    @Override
    public ProductResponse getProductById(Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        Product product = foundProduct.orElseThrow(MemberException.NOT_FOUND::get);

        return new ProductResponse(product);
    }

    //전체 상품 조회
    @Override
    public Page<Product> getAllProducts(MemberPageRequestDTO productPageRequestDTO, Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);
        Product product = foundProduct.orElseThrow(MemberException.NOT_FOUND::get);

        Sort sort = Sort.by("productId").descending();

        Pageable pageable = productPageRequestDTO.getPageable(sort);
        return productRepository.findAll(pageable);
    }

    // 상품 등록
    @Override
    public ProductResponse createProduct(ProductRequest productrequest) {
        try {
            Product product = productrequest.toEntity();
            productRepository.save(product);
            return new ProductResponse(product);
        } catch (Exception e) {
            throw MemberException.NOT_REGISTERED.get();
        }
    }

    // 상품 수정
    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        Optional<Product> foundProduct
                = productRepository.findById(productRequest.);

        Product product = foundProduct.orElseThrow(MemberException.NOT_FOUND::get);
        try {

            return new ProductResponse(product);
        } catch (Exception e){

            throw MemberException.NOT_MODIFIED.get();
        }
    }

    // 상품 삭제
    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);
        Product product = foundProduct.orElseThrow(MemberException.NOT_FOUND::get);

        try {
            productRepository.delete(product);
        } catch (Exception e) {
            throw MemberException.NOT_REMOVED.get();
        }
    }
}
