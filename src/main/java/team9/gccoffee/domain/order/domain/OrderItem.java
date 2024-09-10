package team9.gccoffee.domain.order.domain;

import jakarta.persistence.*;
import lombok.*;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.global.common.BaseTimeEntity;

@Entity
@Builder
@Getter @ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    // 비즈니스 로직
    public void registerOrder(Order order) {
        this.order = order;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void cancel() {
        product.addStockQuantity(quantity);
    }
}