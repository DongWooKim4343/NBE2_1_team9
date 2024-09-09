package team9.gccoffee.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.global.common.BaseTimeEntity;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String postcode;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orderList;

    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePostcode(String postcode) {
        this.postcode = postcode;
    }

    public void changeAddress(String address) {
        this.address = address;
    }
}