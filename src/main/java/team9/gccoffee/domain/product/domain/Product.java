package team9.gccoffee.domain.product.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team9.gccoffee.global.common.BaseTimeEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int price;

    private String description;

    @Column(nullable = false)
    private int stockQuantity;

    //findByProductId를 string값으로
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

    // 비즈니스 로직
    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new IllegalArgumentException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}