package team9.gccoffee.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "주문 생성",
            description = "요청 주문 정보를 바탕으로 주문 생성"
    )
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
    @Operation(
            summary = "관리자 주문 다수 조회",
            description = "모든 고객의 주문 목록 조회"
    )
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderList(
            @Validated @ModelAttribute OrderPageRequest orderPageRequest
    ) {
        List<OrderResponse> orderResponses = orderService.getOrderResponses(orderPageRequest);

        return ResponseEntity.ok(orderResponses);
    }

    // 회원 주문 다수 조회
    @Operation(
            summary = "회원 주문 다수 조회",
            description = "memberId로 해당 고객의 주문 목록 조회"
    )
    @GetMapping("/my-order/{memberId}")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @PathVariable Long memberId,
            @Validated @ModelAttribute OrderPageRequest orderPageRequest
    ) {
        List<OrderResponse> orderResponses = orderService.getMyOrders(memberId, orderPageRequest);

        return ResponseEntity.ok(orderResponses);
    }

    @Operation(
            summary = "주문 정보 조회",
            description = "주문 ID로 특정 주문의 상세 정보 조회"
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable("orderId") Long orderId
    ) {
        OrderResponse orderResponse = orderService.getOrderResponse(orderId);

        return ResponseEntity.ok(orderResponse);
    }

    @Operation(
            summary = "주문 수정",
            description = "주문 ID로 해당 주문의 정보 수정"
    )
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
    @Operation(
            summary = "주문 완료 처리",
            description = "해당 주문 ID의 주문을 완료 상태로 처리"
    )
    @PostMapping("/{orderId}/complete")
    public ResponseEntity<Void> completeOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.completeOrder(orderId);

        return null;
    }

    // 고객 - 주문 취소 메서드
    @Operation(
            summary = "주문 취소 처리",
            description = "해당 주문 ID의 주문을 취소 상태로 처리"
    )
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelorder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(orderId);

        return null;
    }

    @Operation(
            summary = "주문 삭제",
            description = "해당 주문 ID의 주문 삭제"
    )
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> removeOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "주문 항목 조회",
            description = "주문 항목 ID에 해당하는 주문 물품의 세부 정보 조회(이름, 수량, 가격 등)"
    )
    @GetMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getOrderItem(
            @PathVariable("orderItemId") Long orderItemId
    ) {
        OrderItemResponse orderItem = orderService.getOrderItem(orderItemId);

        return ResponseEntity.ok(orderItem);
    }

    @Operation(
            summary = "주문 항목 수정",
            description = "주문 항목 ID에 해당하는 주문 물품의 세부 정보 수정(이름, 수량, 가격 등)"
    )
    @PutMapping("/order-item/{orderItemId}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable("orderItemId") Long orderItemId,
            @Validated @RequestBody OrderItemUpdateDTO orderItemUpdateDTO
    ) {
        OrderItemResponse orderItem = orderService.updateOrderItem(orderItemId, orderItemUpdateDTO);

        return ResponseEntity.ok(orderItem);
    }
}
