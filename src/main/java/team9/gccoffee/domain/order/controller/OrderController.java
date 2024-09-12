package team9.gccoffee.domain.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team9.gccoffee.domain.order.dto.OrderItemResponse;
import team9.gccoffee.domain.order.dto.OrderItemUpdateDTO;
import team9.gccoffee.domain.order.dto.OrderPageRequest;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.dto.OrderUpdateRequest;
import team9.gccoffee.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Validated @RequestBody OrderRequest orderRequest
    ) {
        log.info("OrderController.createOrder() call !!");
        log.info("orderRequest => {}", orderRequest);

        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        return ResponseEntity.ok(orderResponse);
    }

    // 관리자 주문 다수 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderList(
            @Validated @ModelAttribute OrderPageRequest orderPageRequest
            ) {
        List<OrderResponse> orderResponses = orderService.getOrderResponses(orderPageRequest);

        return ResponseEntity.ok(orderResponses);
    }

    // 회원 주문 다수 조회
    @GetMapping("/my-order/{memberId}")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @PathVariable Long memberId,
            @Validated @ModelAttribute OrderPageRequest orderPageRequest
    ) {
        List<OrderResponse> orderResponses = orderService.getMyOrders(memberId, orderPageRequest);

        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable("orderId") Long orderId
    ) {
        OrderResponse orderResponse = orderService.getOrderResponse(orderId);

        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable("orderId") Long orderId,
            @Validated @RequestBody OrderUpdateRequest orderUpdateRequest
    ) {
        log.info("OrderController.updateOrder() call !!");
        log.info("orderUpdateRequest => {}", orderUpdateRequest);

        OrderResponse orderResponse = orderService.updateOrder(orderId, orderUpdateRequest);

        return ResponseEntity.ok(orderResponse);
    }

    // 관리자 - 주문 처리 메서드
    @PostMapping("/{orderId}/complete")
    public ResponseEntity<Void> completeOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.completeOrder(orderId);

        return null;
    }

    // 고객 - 주문 취소 메서드
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(orderId);

        return null;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> removeOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getOrderItem(
            @PathVariable("orderItemId") Long orderItemId
    ) {
        OrderItemResponse orderItem = orderService.getOrderItem(orderItemId);

        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable("orderItemId") Long orderItemId,
            @Validated @RequestBody OrderItemUpdateDTO orderItemUpdateDTO
    ) {
        OrderItemResponse orderItem = orderService.updateOrderItem(orderItemId, orderItemUpdateDTO);

        return ResponseEntity.ok(orderItem);
    }
}
