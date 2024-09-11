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
import team9.gccoffee.domain.order.domain.OrderStatus;
import team9.gccoffee.domain.order.dto.OrderItemRequest;
import team9.gccoffee.domain.order.dto.OrderItemResponse;
import team9.gccoffee.domain.order.dto.OrderItemUpdateDTO;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.dto.OrderUpdateRequest;
import team9.gccoffee.domain.order.repository.OrderItemRepository;
import team9.gccoffee.domain.order.repository.OrderRepository;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.member.MemberException;
import team9.gccoffee.global.exception.order.OrderException;
import team9.gccoffee.global.exception.product.ProductException;

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
        List<OrderItem> orderItems = getOrderItems(orderRequest.getOrderItemRequests());

        List<OrderItem> savedOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            savedOrderItems.add(orderItemRepository.save(orderItem));
        }

        // 주문 생성
        Order order = Order.createOrder(member, savedOrderItems, orderRequest.getAddress(), orderRequest.getPostcode());

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
                .orElseThrow(ProductException.NOT_FOUND::get);

        product.removeStock(orderItemRequest.getQuantity());

        if (!orderItemRequest.getCategory().equals(product.getCategory())
                || orderItemRequest.getPrice() != product.getPrice()) {
            throw ProductException.BAD_REQUEST.get();
        }

        return product;
    }

    public OrderResponse getOrderResponse(Long orderId) {
        return orderRepository.getOrderResponse(orderId)
                .orElseThrow(OrderException.ORDER_NOT_FOUND::get);
    }

    public List<OrderResponse> getOrderResponses() {
        List<OrderResponse> orderResponseList = orderRepository.getOrderResponseList();

        if (orderResponseList.isEmpty()) {
            throw OrderException.ORDER_NOT_FOUND.get();
        }

        return orderResponseList;
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderException.ORDER_NOT_FOUND::get);

        order.changeAddress(orderUpdateRequest.getAddress());
        order.changePostcode(orderUpdateRequest.getPostcode());

        return new OrderResponse(order);
    }

    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderException.ORDER_NOT_FOUND::get);

        if (order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus()
                .equals(OrderStatus.COMPLETED)) {
            throw OrderException.ORDER_CLOSED.get();
        }

        order.changeOrderStatus(OrderStatus.COMPLETED);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderException.ORDER_NOT_FOUND::get);

        if (order.getOrderStatus().equals(OrderStatus.COMPLETED)) {
            throw OrderException.ORDER_COMPLETED.get();
        }

        order.cancel();
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderException.ORDER_NOT_FOUND::get);

        if (!order.getOrderStatus().equals(OrderStatus.CANCELLED)){
            throw OrderException.ORDER_NOT_CANCELED.get();
        }

        orderRepository.delete(order);
    }

    public OrderItemResponse getOrderItem(Long orderItemId) {
        OrderItemResponse orderItemResponse = orderItemRepository.getOrderItemResponse(orderItemId)
                .orElseThrow(OrderException.ORDER_ITEM_NOT_FOUND::get);

        return orderItemResponse;
    }

    public OrderItemResponse updateOrderItem(Long orderItemId, OrderItemUpdateDTO orderItemUpdateDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(OrderException.ORDER_ITEM_NOT_FOUND::get);

        orderItem.changeQuantity(orderItemUpdateDTO.getQuantity());

        return new OrderItemResponse(orderItem);
    }
}
