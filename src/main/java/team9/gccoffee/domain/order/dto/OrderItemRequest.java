package team9.gccoffee.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team9.gccoffee.domain.product.domain.Category;

@Getter
@ToString
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Category category;

    @Min(1000)
    private int price;

    @Min(5)
    private int quantity;
}
