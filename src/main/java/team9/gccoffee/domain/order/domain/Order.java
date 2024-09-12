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
    private String postcode;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void registerMember(Member member) {
        this.member = member;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }

        return totalPrice;
    }

    public void cancel(){
        changeOrderStatus(OrderStatus.CANCELLED);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 주문 생성 메서드
    public static Order createOrder(Member member, List<OrderItem> orderItems, String address, String postcode) {
        Order order = new Order();

        order.postcode = postcode;
        order.address = address;
        order.orderStatus = OrderStatus.CREATED;
        order.registerMember(member);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.registerOrder(this);
    }
}