package team9.gccoffee.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team9.gccoffee.domain.order.domain.Order;

public class OrderRepository extends JpaRepository<Order, Long> {
}
