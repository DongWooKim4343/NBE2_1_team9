package team9.gccoffee.domain.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import team9.gccoffee.domain.order.domain.OrderItem;

@Getter
@ToString
@AllArgsConstructor
public class OrderRequest {

    private Long memberId;

    private List<OrderItem> orderItems;

    private String address;
}
