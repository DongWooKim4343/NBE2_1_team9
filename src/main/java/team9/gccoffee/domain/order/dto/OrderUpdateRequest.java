package team9.gccoffee.domain.order.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateRequest {

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String address;
}
