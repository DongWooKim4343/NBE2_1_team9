package team9.gccoffee.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private List<OrderItemRequest> orderItems;

    @NotNull
    private String postcode;

    @NotNull
    private String address;
}
