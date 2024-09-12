package team9.gccoffee.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import team9.gccoffee.domain.order.dto.OrderPageRequest;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.dto.OrderUpdateRequest;
import team9.gccoffee.domain.order.repository.OrderItemRepository;
import team9.gccoffee.domain.order.repository.OrderRepository;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

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
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

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
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.removeStock(orderItemRequest.getQuantity());

        if (!orderItemRequest.getCategory().equals(product.getCategory())
                || orderItemRequest.getPrice() != product.getPrice()) {
            throw new GcCoffeeCustomException(ErrorCode.PRODUCT_BAD_REQUEST);
        }

        return product;
    }

    public OrderResponse getOrderResponse(Long orderId) {
        return orderRepository.getOrderResponse(orderId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    public List<OrderResponse> getOrderResponses(OrderPageRequest orderPageRequest) {
        Sort sort = Sort.by("orderId").ascending();
        Pageable pageAble = orderPageRequest.getPageAble(sort);

        List<OrderResponse> orderResponseList = orderRepository.getOrderResponseList(pageAble);

        if (orderResponseList.isEmpty()) {
            throw  new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orderResponseList;
    }

    public List<OrderResponse> getMyOrders(Long memberId, OrderPageRequest orderPageRequest) {
        memberRepository.findById(memberId).orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageAble = orderPageRequest.getPageAble(sort);

        List<OrderResponse> orderResponseList = orderRepository.getOrderResponseListByMemberId(memberId, pageAble);

        if (orderResponseList.isEmpty()) {
            throw  new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orderResponseList;
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND));

        order.changeAddress(orderUpdateRequest.getAddress());
        order.changePostcode(orderUpdateRequest.getPostcode());

        return new OrderResponse(order);
    }

    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus()
                .equals(OrderStatus.COMPLETED)) {
            throw new GcCoffeeCustomException(ErrorCode.ORDER_ALREADY_CLOSED);
        }

        order.changeOrderStatus(OrderStatus.COMPLETED);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus().equals(OrderStatus.COMPLETED)) {
            throw new GcCoffeeCustomException(ErrorCode.ORDER_ALREADY_COMPLETED);
        }

        order.cancel();
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getOrderStatus().equals(OrderStatus.CANCELLED)){
            throw new GcCoffeeCustomException(ErrorCode.ORDER_NOT_CANCELED);
        }

        orderRepository.delete(order);
    }

    public OrderItemResponse getOrderItem(Long orderItemId) {
        return orderItemRepository.getOrderItemResponse(orderItemId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_ITEM_NOT_FOUND));
    }

    public OrderItemResponse updateOrderItem(Long orderItemId, OrderItemUpdateDTO orderItemUpdateDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        orderItem.changeQuantity(orderItemUpdateDTO.getQuantity());

        return new OrderItemResponse(orderItem);
    }
}
