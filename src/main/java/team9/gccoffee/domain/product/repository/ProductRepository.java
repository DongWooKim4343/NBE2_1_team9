package team9.gccoffee.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team9.gccoffee.domain.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(Long id);

    Product findByProductName(String productName); //테스트 코드 작성시 사용함.
}
