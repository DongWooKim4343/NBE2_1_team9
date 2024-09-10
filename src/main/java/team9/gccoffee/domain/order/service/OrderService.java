package team9.gccoffee.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.repository.OrderItemRepository;
import team9.gccoffee.domain.order.repository.OrderRepository;

@Controller
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(Member member, OrderRequest orderRequest) {


        // 주문 생성
        Order order = Order.createOrder(member, orderItems, orderRequest.getAddress(), orderRequest.getPostcode());

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 주문 아이템 저장
    }
}
