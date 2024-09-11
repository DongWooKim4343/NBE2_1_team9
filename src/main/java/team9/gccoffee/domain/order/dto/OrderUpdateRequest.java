package team9.gccoffee.domain.order.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class OrderUpdateRequest {

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String address;
}
