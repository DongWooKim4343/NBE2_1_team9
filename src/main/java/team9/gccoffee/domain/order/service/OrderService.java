package team9.gccoffee.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.domain.OrderItem;
import team9.gccoffee.domain.order.dto.OrderItemRequest;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.repository.OrderItemRepository;
import team9.gccoffee.domain.order.repository.OrderRepository;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.MemberException;

@Controller
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {

        // 멤버 생성 및 유효성 검사
        Member member = memberRepository.findById(orderRequest.getMemberId())
                .orElseThrow(MemberException.NOT_FOUND::get);

        // 주문 아이템 생성 및 저장
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItem : getOrderItems(orderRequest.getOrderItems())) {
            orderItems.add(orderItemRepository.save(orderItem));
        }

        // 주문 생성
        Order order = Order.createOrder(member, orderItems, orderRequest.getAddress(), orderRequest.getPostcode());

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(savedOrder);
    }

    private List<OrderItem> getOrderItems(List<OrderItemRequest> orderItemRequests) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Product product = validateProduct(orderItemRequest);
            orderItems.add(OrderItem.createOrderItem(product, orderItemRequest.getQuantity()));
        }

        return orderItems;
    }

    private Product validateProduct(OrderItemRequest orderItemRequest) {
        Product product = productRepository.findById(orderItemRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!orderItemRequest.getCategory().equals(product.getCategory())
                || orderItemRequest.getPrice() != product.getPrice()) {
            throw new IllegalArgumentException("Product validation failed");
        }

        return product;
    }

    public OrderResponse getOrderResponse(Long orderId) {
        return orderRepository.getOrderResponse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
