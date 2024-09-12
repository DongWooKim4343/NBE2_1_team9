package team9.gccoffee.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ProductServiceImplTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    Product product1;
    Product product2;
    Product saveProduct1;
    Product saveProduct2;

    @BeforeEach
        //더미데이터 만들고 시작
    void dummyInsert() {
        //더미데이터
        product1 = Product.builder()
                .productId(1L)
                .productName("커피더미1")
                .price(5000)
                .description("커피더미1의 설명")
                .category(Category.COFFEE1)
                .stockQuantity(100)
                .build();
        product2 = Product.builder()
                .productId(2L)
                .productName("커피더미22")
                .price(50050)
                .description("커피더미122의 설명")
                .category(Category.COFFEE2)
                .stockQuantity(10000)
                .build();



        //더미데이터 DB에 저장하는 코드
        saveProduct1 = productRepository.save(product1);
        saveProduct2 = productRepository.save(product2);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    @Test
    void getProductById() {
        // Id로 찾은 상품의 객체
        ProductResponse findProduct1 = productService.getProductById(saveProduct1.getProductId());
        ProductResponse findProduct2 = productService.getProductById(saveProduct2.getProductId());
        //테스트 시작
        assertThat(saveProduct1.getCategory()).isEqualTo(Category.COFFEE1);
        assertThat(findProduct1.getProductName()).isEqualTo(saveProduct1.getProductName());
        assertThat(findProduct1.getPrice()).isEqualTo(saveProduct1.getPrice());
        assertThat(findProduct1.getCategory()).isEqualTo(saveProduct1.getCategory());
        assertThat(findProduct1.getDescription()).isEqualTo(saveProduct1.getDescription());
        assertThat(findProduct1.getStockQuantity()).isEqualTo(saveProduct1.getStockQuantity());

        assertThat(saveProduct2.getCategory()).isEqualTo(Category.COFFEE2);
        assertThat(findProduct2.getProductName()).isEqualTo(saveProduct2.getProductName());
        assertThat(findProduct2.getPrice()).isEqualTo(saveProduct2.getPrice());
        assertThat(findProduct2.getCategory()).isEqualTo(saveProduct2.getCategory());
        assertThat(findProduct2.getDescription()).isEqualTo(saveProduct2.getDescription());
        assertThat(findProduct2.getStockQuantity()).isEqualTo(saveProduct2.getStockQuantity());



    }


    @Test
    void getAllProducts() {
        MemberPageRequestDTO requestDTO = MemberPageRequestDTO.builder()
                .page(1)
                .size(20)
                .build();
        //MemberPageRequestDTO를 Product에서도 사용함
        //더미를 리스트에 추가
        List<Product> productList = Arrays.asList(saveProduct1, saveProduct2);

        Page<Product> allProducts = productService.getAllProducts(requestDTO);
        assertThat(allProducts).isNotNull();
        assertThat(allProducts.getTotalElements()).isEqualTo(productList.size());
    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}