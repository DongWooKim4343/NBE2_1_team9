package team9.gccoffee.domain.order.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class OrderItemUpdateDTO {

    @Min(1)
    private int quantity;
}
