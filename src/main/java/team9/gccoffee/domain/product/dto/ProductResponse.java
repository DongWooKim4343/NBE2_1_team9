package team9.gccoffee.domain.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;

@Data
@NoArgsConstructor
public class ProductResponse {

    private String productName;
    private Category category;
    private int price;
    private int stockQuantity;
    private String description;

    public ProductResponse(Product product) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
    }
}
