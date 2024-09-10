package team9.gccoffee.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team9.gccoffee.domain.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
