package team9.gccoffee.domain.product.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;

@Data
@NoArgsConstructor
public class ProductRequest {

    private String productName;

    private int price;

    private String description;

    private int stockQuantity;

    private Category category;

    public Product toEntity() {
        return Product.builder().productName(productName)
                .price(price)
                .description(description)
                .stockQuantity(stockQuantity)
                .category(category).build();
    }



}
