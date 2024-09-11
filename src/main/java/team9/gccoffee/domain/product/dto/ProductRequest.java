package team9.gccoffee.domain.product.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;

@Data
@NoArgsConstructor
public class ProductRequest {


    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String productName;

    @NotEmpty(message = "카테고리는 필수 입력값입니다.")
    private Category category;

    @NotEmpty(message = "가격은 필수 입력값입니다.")
    private int price;

    @NotEmpty(message = "설명은 필수 입력값입니다.")
    private String description;

    @NotEmpty(message = "재고수량은 필수 입력값입니다.")
    private int stockQuantity;

    public Product toEntity() {
        return Product.builder().productName(productName)
                .price(price)
                .description(description)
                .stockQuantity(stockQuantity)
                .category(category).build();
    }
}
