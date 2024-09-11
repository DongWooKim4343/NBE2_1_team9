package team9.gccoffee.domain.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team9.gccoffee.domain.order.dto.OrderItemResponse;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest orderRequest
            ) {
        log.info("OrderController.createOrder() call !!");
        log.info("orderRequest => {}", orderRequest);


        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderList() {

        return null;
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
            @PathVariable("orderId") Long orderId
    ) {

        return null;
    }

    // 관리자 - 주문 처리 메서드
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponse> completeOrder(
            @PathVariable("orderId") Long orderId
    ) {

        return null;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> removeOrder(
            @PathVariable("orderId") Long orderId
    ) {

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getOrderItem(
            @PathVariable("orderItemId") Long orderItemId
    ) {

        return null;
    }

    @PutMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable("orderItemId") Long orderItemId
    ) {

        return null;
    }
}
