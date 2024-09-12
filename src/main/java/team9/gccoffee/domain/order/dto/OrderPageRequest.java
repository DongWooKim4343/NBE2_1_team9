package team9.gccoffee.domain.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class OrderPageRequest {

    @Min(1)
    private int page = 1;

    @Min(10)
    @Max(100)
    private int size = 10;

    public Pageable getPageAble(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}