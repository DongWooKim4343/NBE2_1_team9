package team9.gccoffee.domain.order.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.*;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.global.common.BaseTimeEntity;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter @ToString(exclude = {"orderItems", "member"})
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    private int totalPrice;

    public void registerMember(Member member) {
        this.member = member;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    // 주문 생성 메서드
    public static Order createOrder(Member member, List<OrderItem> orderItems, String address) {
        Order order = new Order();

        order.address = address;
        order.orderStatus = OrderStatus.CREATED;

        order.changeMember(member);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }
}