package team9.gccoffee.domain.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.domain.OrderItem;
import team9.gccoffee.domain.order.domain.OrderStatus;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("Order 를 저장할 수 있어야 한다.")
    @org.junit.jupiter.api.Order(1)
    @Commit
    void createOrderTest() {
        // given
        Member savedMember = createTestMember();
        Product savedProduct = createTestProduct("test product", 1000, Category.COFFEE1, "test", 2);
        Product savedProduct2 = createTestProduct("test product 2", 2000, Category.COFFEE2, "test 2", 1);

        List<OrderItem> orderItems = createTestOrderItems(savedProduct, savedProduct2);

        String address = "서울시 종로구";
        String postcode = "11111";

        // when
        Order order = Order.createOrder(savedMember, orderItems, address, postcode);
        Order savedOrder = orderRepository.save(order);

        // then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(savedOrder.getMember().getName()).isEqualTo(savedMember.getName());
        assertThat(savedOrder.getOrderItems().size()).isEqualTo(orderItems.size());
    }

    @Test
    @DisplayName("Order 를 조회할 수 있어야 한다.")
    @org.junit.jupiter.api.Order(2)
    void selectOrderTest() {
        // given
        Long orderId = 1L;

        // when
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        // then
        assertThat(orderOptional.isPresent()).isTrue();
        Order findOrder = orderOptional.get();

        assertThat(findOrder.getMember().getName()).isEqualTo("tester");
        assertThat(findOrder.getOrderItems().size()).isEqualTo(2);
        assertThat(findOrder.getOrderItems().get(0).getProduct().getProductName()).isEqualTo("test product");
    }

    @Test
    @DisplayName("OrderItem 의 수량을 수정할 수 있어야 한다.")
    @org.junit.jupiter.api.Order(3)
    void updateOrderItemTest() {
        // given
        OrderItem orderItem = orderItemRepository.findById(1L).get();

        // when
        orderItem.changeQuantity(2);

        // then
        orderItem = orderItemRepository.findById(1L).get();
        assertThat(orderItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("Order 를 수정할 수 있어야 한다.")
    @org.junit.jupiter.api.Order(4)
    void updateOrderTest() {
        // given
        Order order = orderRepository.findById(1L).get();
        String newAddress = "서울시 서초구";

        // when
        order.changeAddress(newAddress);
        order.changeOrderStatus(OrderStatus.COMPLETED);
        Order updateOrder = orderRepository.findById(1L).get();

        // then
        assertThat(updateOrder.getAddress()).isEqualTo(newAddress);
        assertThat(updateOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("Order 를 삭제할 수 있어야 한다.")
    @org.junit.jupiter.api.Order(5)
    void deleteOrderTest() {
        // given
        Order order = orderRepository.findById(1L).get();

        // when
        orderRepository.delete(order);

        // then
        Optional<Order> optionalOrder = orderRepository.findById(1L);
        assertThat(optionalOrder.isEmpty()).isTrue();

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(1L);
        assertThat(orderItems.isEmpty()).isTrue();
    }

    // 멤버 생성 로직 분리
    private Member createTestMember() {
        Member member = Member.builder()
                .name("tester")
                .email("email@naver.com")
                .postcode("11221")
                .address("서울시 종로구")
                .memberType(MemberType.ADMIN)
                .build();
        return memberRepository.save(member);
    }

    // 상품 생성 로직 분리
    private Product createTestProduct(String name, int price, Category category, String description, int stockQuantity) {
        Product product = Product.builder()
                .productName(name)
                .price(price)
                .category(category)
                .description(description)
                .stockQuantity(stockQuantity)
                .build();

        return productRepository.save(product);
    }

    // 주문 아이템 생성 로직 분리
    private List<OrderItem> createTestOrderItems(Product product1, Product product2) {
        OrderItem orderItem1 = OrderItem.builder()
                .product(product1)
                .category(product1.getCategory())
                .price(product1.getPrice())
                .quantity(2)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .product(product2)
                .category(product2.getCategory())
                .price(product2.getPrice())
                .quantity(2)
                .build();

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        return Arrays.asList(orderItem1, orderItem2);
    }
}