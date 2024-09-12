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
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.dto.ProductRequest;
import team9.gccoffee.domain.product.dto.ProductResponse;
import team9.gccoffee.domain.product.dto.ProductUpdateRequest;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class ProductServiceImplTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberRepository memberRepository;

    Product product1;
    Product product2;
    Product saveProduct1;
    Product saveProduct2;
    Member customer;
    Member admin;
    Member saveMember1;
    Member saveMember2;

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

        // CUSTOMER멤버, ADMIN멤버 생성
        customer = Member.builder()
                .memberId(1L)
                .memberType(MemberType.CUSTOMER)
                .address("NOT NULL")
                .email("NOT NULL")
                .postcode("NOT NULL")
                .name("NOT NULL")
                .build();
        admin = Member.builder()
                .memberId(2L)
                .memberType(MemberType.ADMIN)
                .address("NOT NULL")
                .email("NOT NULL")
                .postcode("NOT NULL")
                .name("NOT NULL")
                .build();


        //더미데이터 DB에 저장하는 코드
        saveProduct1 = productRepository.save(product1);
        saveProduct2 = productRepository.save(product2);
        saveMember1 = memberRepository.save(customer);
        saveMember2 = memberRepository.save(admin);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
        memberRepository.deleteAll();
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
        MemberPageRequestDTO requestDTO = new MemberPageRequestDTO();
        //MemberPageRequestDTO를 Product에서도 사용함
        //더미를 리스트에 추가
        List<Product> productList = Arrays.asList(saveProduct1, saveProduct2);

        Page<Product> allProducts = productService.getAllProducts(requestDTO);
        assertThat(allProducts).isNotNull();
        assertThat(allProducts.getTotalElements()).isEqualTo(productList.size());
    }

    @Test
    void createProduct() {
        ProductRequest requestDTO = new ProductRequest();
        ProductRequest requestDTO2 = new ProductRequest();


        //고객이 신규상품 5000원짜리를 100개 등록시도
        requestDTO.setMemberId(saveMember1.getMemberId());
        requestDTO.setProductName("신규상품");
        requestDTO.setDescription("신규상품에 관한 설명");
        requestDTO.setCategory(Category.COFFEE1);
        requestDTO.setPrice(5000);
        requestDTO.setStockQuantity(100);

        //관리자가 다른상품 등록시도
        requestDTO2.setMemberId(saveMember2.getMemberId());
        requestDTO2.setProductName("다른상품");
        requestDTO2.setDescription("또 다른 상품에 관한 설명");
        requestDTO2.setCategory(Category.COFFEE2);
        requestDTO2.setPrice(12345);
        requestDTO2.setStockQuantity(11200);

        // 고객이 상품 등록 시도 -> 예외 발생
        assertThatThrownBy(() -> productService.createProduct(requestDTO))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessageContaining(ErrorCode.MEMBER_NOT_ADMIN.getMessage());
        //관리자가 등록시도
        ProductResponse createProduct2 = productService.createProduct(requestDTO2);
        //DB에 들어있나 확인
        Product productDB2 = productRepository.findByProductName(createProduct2.getProductName());

        //테스트
        assertThat(productDB2).isNotNull();
        assertThat(productDB2.getCategory()).isEqualTo(Category.COFFEE2);
        assertThat(productDB2.getStockQuantity()).isEqualTo(createProduct2.getStockQuantity());
        assertThat(productDB2.getDescription()).isEqualTo(createProduct2.getDescription());
        assertThat(productDB2.getPrice()).isEqualTo(createProduct2.getPrice());
    }

    @Test
    void updateProduct() {

        //DTO를 사용하여 수정 고객
        ProductUpdateRequest updateDTO = new ProductUpdateRequest();
        updateDTO.setProductId(saveProduct1.getProductId());
        updateDTO.setMemberId(saveMember1.getMemberId());
        updateDTO.setProductName("수정수정");
        updateDTO.setPrice(1231232131);
        updateDTO.setCategory(Category.ETC);
        updateDTO.setDescription("수정");
        updateDTO.setStockQuantity(100000);

        //DTO를 사용하여 수정 관리자
        ProductUpdateRequest updateDTO2 = new ProductUpdateRequest();
        updateDTO2.setProductId(saveProduct1.getProductId());
        updateDTO2.setMemberId(saveMember2.getMemberId());
        updateDTO2.setProductName("수정수정");
        updateDTO2.setPrice(1231232131);
        updateDTO2.setCategory(Category.ETC);
        updateDTO2.setDescription("수정");
        updateDTO2.setStockQuantity(100000);

        // 고객이 수정할때 예외 발생 확인
        assertThatThrownBy(() -> productService.updateProduct(updateDTO))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessageContaining(ErrorCode.MEMBER_NOT_ADMIN.getMessage());

        //관리자가 수정할때 검증
        productService.updateProduct(updateDTO2);

        // DB에서 직접 조회하여 검증
        Product foundProduct = productRepository.findById(saveProduct1.getProductId()).orElse(null);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getCategory()).isEqualTo(Category.ETC);
        assertThat(foundProduct.getDescription()).isEqualTo(updateDTO.getDescription());
        assertThat(foundProduct.getPrice()).isEqualTo(updateDTO.getPrice());
        assertThat(foundProduct.getProductName()).isEqualTo(updateDTO.getProductName());
        assertThat(foundProduct.getStockQuantity()).isEqualTo(updateDTO.getStockQuantity());
    }

    @Test
    void deleteProduct() {

        productService.deleteProduct(saveProduct1.getProductId());
        productService.deleteProduct(saveProduct2.getProductId());

        assertThat(productRepository.findAll()).isEmpty();
        //assertThatThrownBy(() -> memberRepository.findAll()).isInstanceOf(MemberTaskException.class);

        // 삭제된 상품을 조회할 때 예외 발생 확인
        assertThatThrownBy(() -> productService.getProductById(product1.getProductId()))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessageContaining(ErrorCode.PRODUCT_NOT_FOUND.getMessage());

        assertThatThrownBy(() -> productService.getProductById(product2.getProductId()))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessageContaining(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }
}