package team9.gccoffee.domain.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    //개별 상품 조회
    @Override
    public ProductResponse getProductById(Long productId) {
        Optional<Product> foundProduct = productRepository.findByProductId(productId);
//        Product product = foundProduct.orElseThrow(() -> new ChangeSetPersister.NotFoundException("Product not found"));
        //exception 처리 해야함
        return new ProductResponse(foundProduct.orElse(null));
    }

    // 전체 상품 조회
    @Override
    public Page<Product> getAllProducts(ProductRequest productPageRequest) {
        Sort sort = Sort.by("ProductId").descending();

        return null;
    }

    // 상품 생성
    @Override
    public ProductResponse createProduct(ProductRequest productrequest) {
        return null;
    }

    // 상품 수정
    @Override
    public ProductResponse updateProduct(ProductRequest productrequest) {
        return null;
    }

    // 상품 삭제
    @Override
    public void deleteProduct(Long productId) {

    }
}
