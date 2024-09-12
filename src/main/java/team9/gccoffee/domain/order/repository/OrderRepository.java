package team9.gccoffee.domain.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.dto.OrderResponse;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
    Optional<OrderResponse> getOrderResponse(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o")
    List<OrderResponse> getOrderResponseList(Pageable pageable);

    @Query("SELECT o from Order o WHERE o.member.memberId = :memberId")
    List<OrderResponse> getOrderResponseListByMemberId(@Param("memberId") Long memberId, Pageable pageAble);
}
