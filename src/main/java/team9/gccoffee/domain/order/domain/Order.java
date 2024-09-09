package team9.gccoffee.domain.order.domain;

import jakarta.persistence.*;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.global.common.BaseTimeEntity;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private int totalPrice;
}







