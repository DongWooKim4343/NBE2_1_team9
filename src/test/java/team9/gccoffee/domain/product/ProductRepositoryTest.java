package team9.gccoffee.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    //등록
    @Test
    public void testInsert() {
        //given
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Product product = Product.builder()
                    .productName("product" + i)
                    .category(i <= 10 ? Category.COFFEE1 : Category.COFFEE2)
                    .price(5000)
                    .description("description" + i)
                    .stockQuantity(50)
                    .build();

            //when
            Product savedProduct = productRepository.save(product);

            //then
            assertNotNull(savedProduct);
            if(i<=10) assertThat(savedProduct.getCategory()).isEqualTo(Category.COFFEE1);
            else      assertThat(savedProduct.getCategory()).isEqualTo(Category.COFFEE2);

        });
    }

    //조회
    // 상품 개별 조회
    @Test
    public void testRead() {
        //given
        Long productId = 3L;

        //when
        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent());

        //then
        Product product = foundProduct.get();
        assertThat(product.getCategory()).isEqualTo(Category.COFFEE1);

    }

    // 상품 전체 조회 - 페이징
    @Test
    public void testList() {
        //given
        Pageable pageable = PageRequest.of(0,
                20, Sort.by("productId").ascending());
        //when
        Page<Product> productPage = productRepository.findAll(pageable);
        assertNotNull( productPage );
        //then
        assertThat(productPage.getTotalElements()).isEqualTo(20);
        assertThat(productPage.getTotalPages()).isEqualTo(1);
        assertThat(productPage.getNumber()).isEqualTo(0);
        assertThat(productPage.getSize()).isEqualTo(20);

        productPage.getContent().forEach(System.out::println);

    }

    //수정
    @Test
    @Transactional
    @Commit
    public void testUpdate() {
        //given
        Long productId = 2L;

        //when
        Optional<Product> foundProduct = productRepository.findById(productId);
        foundProduct.orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND));

        foundProduct.get().changeProductName("Product Changed");
        foundProduct.get().changeCategory(Category.COFFEE2);
        foundProduct.get().changePrice(10000);
        foundProduct.get().changeDescription("changedDescription");
        foundProduct.get().changeStockQuantity(10);

        //then
        assertThat(foundProduct.get().getCategory()).isEqualTo(Category.COFFEE2);
        assertThat(foundProduct.get().getProductName()).isEqualTo("Product Changed");

    }


    //삭제
    @Test
    public void testDeleteById() {
        //given
        Long productId = 4L;

        //when
        assertTrue(productRepository.findById(productId).isPresent());
        productRepository.deleteById(productId);

        //then
        assertFalse(productRepository.findById(productId).isPresent());

    }


}
