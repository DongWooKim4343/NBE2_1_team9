package team9.gccoffee.domain.order.dto;

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

    private List<OrderItem> orderItems;

    private int totalPrice;

    public OrderResponse(Order order) {
        Member member = order.getMember();
        email = member.getEmail();
        postcode = member.getPostcode();
        address = order.getAddress();
        orderItems = order.getOrderItems();
        totalPrice = order.getTotalPrice();
    }
}
