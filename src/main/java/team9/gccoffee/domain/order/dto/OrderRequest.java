package team9.gccoffee.domain.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.domain.OrderItem;
import team9.gccoffee.domain.order.domain.OrderStatus;

@Getter
@ToString
@AllArgsConstructor
public class OrderRequest {

    private Long memberId;

    private List<OrderItem> orderItems;

    private String address;

//    public Order toEntity() {
//        return Order.builder()
//                .member(Member.builder().memberId(memberId).build())
//                .orderStatus(OrderStatus.CREATED)
//                .orderItems(orderItems)
//                .address(address)
//                .totalPrice(getTotalPrice())
//                .build();
//    }

    // 총 가격 계산하는 로직
    private int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }
        return totalPrice;
    }
}
