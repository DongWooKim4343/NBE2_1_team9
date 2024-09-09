package team9.gccoffee.domain.member.domain;

import jakarta.persistence.*;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.global.common.BaseTimeEntity;

import java.util.List;

@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String postcode;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orderList;

    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;
}
