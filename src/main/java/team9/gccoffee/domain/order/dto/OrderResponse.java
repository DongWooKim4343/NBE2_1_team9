package team9.gccoffee.domain.order.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.domain.OrderItem;

@Getter @ToString
@NoArgsConstructor
public class OrderResponse {

    private String email;

    private String address;

    private String postcode;

    private List<OrderItemResponse> orderItemResponses;

    private int totalPrice;

    public OrderResponse(Order order) {
        Member member = order.getMember();
        email = member.getEmail();
        postcode = order.getPostcode();
        address = order.getAddress();
        orderItemResponses = getOrderItemResponse(order.getOrderItems());
        totalPrice = order.getTotalPrice();
    }

    private List<OrderItemResponse> getOrderItemResponse(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(new OrderItemResponse(orderItem));
        }
        return orderItemResponses;
    }
}
