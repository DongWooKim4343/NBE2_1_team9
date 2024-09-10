package team9.gccoffee.domain.order.dto;

import lombok.Getter;
import team9.gccoffee.domain.order.domain.OrderItem;
import team9.gccoffee.domain.product.domain.Category;

@Getter
public class OrderItemResponse {

    private Long productId;

    private Category category;

    private int price;

    private int quantity;

    public OrderItemResponse(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.category = orderItem.getCategory();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
