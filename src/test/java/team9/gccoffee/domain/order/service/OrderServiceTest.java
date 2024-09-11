package team9.gccoffee.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.domain.order.domain.OrderItem;
import team9.gccoffee.domain.order.domain.OrderStatus;
import team9.gccoffee.domain.order.dto.OrderItemRequest;
import team9.gccoffee.domain.order.dto.OrderRequest;
import team9.gccoffee.domain.order.dto.OrderResponse;
import team9.gccoffee.domain.order.dto.OrderUpdateRequest;
import team9.gccoffee.domain.order.repository.OrderItemRepository;
import team9.gccoffee.domain.order.repository.OrderRepository;
import team9.gccoffee.domain.product.domain.Category;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;

@SpringBootTest
@Slf4j
class OrderServiceTest {

    @Autowired private OrderService orderService;

    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private MemberRepository memberRepository;

    static Member member;
    Product product;
    Product product2;
    List<OrderItem> orderItems;

    @BeforeEach
    void insertTestData() {
        log.info("-- insertTestData");
        member = createTestMember();
        product = createTestProduct("test product", 1000, Category.COFFEE1, "test", 5);
        product2 = createTestProduct("test product 2", 2000, Category.COFFEE2, "test 2", 5);
        orderItems = createTestOrderItems(product, product2);
    }

    @Test
    @DisplayName("OrderRequest 객체를 통해 Order 를 생성할 수 있어야 한다.")
    void createOrderTest() {
        // given
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        orderItemRequests.add(new OrderItemRequest(1L, Category.COFFEE1, 1000, 2));
        orderItemRequests.add(new OrderItemRequest(2L, Category.COFFEE2, 2000, 1));

        String postcode = "12345";
        String address = "서울시 종로구";

        OrderRequest orderRequest = new OrderRequest(member.getMemberId(), orderItemRequests, postcode, address);

        // when
        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        // then
        assertThat(orderResponse.getPostcode()).isEqualTo(postcode);
        assertThat(orderResponse.getAddress()).isEqualTo(address);
        assertThat(orderResponse.getEmail()).isEqualTo(member.getEmail());
        assertThat(orderResponse.getTotalPrice()).isEqualTo(orderItemRequests.stream()
                .mapToInt(orderItemRequest -> orderItemRequest.getPrice() * orderItemRequest.getQuantity())
                .sum()
        );
        assertThat(productRepository.findById(1L).get().getStockQuantity()).isEqualTo(3);
        assertThat(productRepository.findById(2L).get().getStockQuantity()).isEqualTo(4);
    }

    @Test
    @DisplayName("Order 를 생성할 때, 주문 수량이 상품의 재고보다 많다면 IllegalException 이 발생한다.")
    void createOrderProductStockOverExceptionTest() {
        // given
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        orderItemRequests.add(new OrderItemRequest(1L, Category.COFFEE1, 1000, 10));
        orderItemRequests.add(new OrderItemRequest(2L, Category.COFFEE2, 2000, 10));

        String postcode = "12345";
        String address = "서울시 종로구";

        OrderRequest orderRequest = new OrderRequest(member.getMemberId(), orderItemRequests, postcode, address);
        log.info("orderRequest => {}", orderRequest);

        // then
        assertThatThrownBy(() -> orderService.createOrder(orderRequest))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("need more stock");
    }

    @Test
    @DisplayName("모든 주문 조회를 할 수 있어야 한다.")
    void getOrderListExceptionTest() {
        // when
        List<OrderResponse> orderResponses = orderService.getOrderResponses();

        // then
        assertThat(orderResponses.isEmpty()).isFalse();
        assertThat(orderResponses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("OrderUpdateRequest 객체를 통해 Order 를 수정할 수 있어야 한다.")
    void updateOrderTest() {
        // given
        Long orderId = 1L;

        String updatePostcode = "33333";
        String updateAddress = "부산시 금정구";

        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest(updatePostcode, updateAddress);

        // when
        OrderResponse orderResponse = orderService.updateOrder(orderId, orderUpdateRequest);

        // then
        assertThat(orderResponse.getPostcode()).isEqualTo(updatePostcode);
        assertThat(orderResponse.getAddress()).isEqualTo(updateAddress);
    }

    @Test
    @DisplayName("Order 를 주문 처리할 수 있어야 한다.")
    void completeOrderTest() {
        // given
        Long orderId = 1L;

        // when
        orderService.completeOrder(orderId);
        Order order = orderRepository.findById(orderId).get();

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("Order 를 주문 취소할 수 있어야 한다.")
    void cancelOrderTest() {
        // given
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        orderItemRequests.add(new OrderItemRequest(1L, Category.COFFEE1, 1000, 2));
        orderItemRequests.add(new OrderItemRequest(2L, Category.COFFEE2, 2000, 1));

        String postcode = "54321";
        String address = "경기도 안산시";

        OrderRequest orderRequest = new OrderRequest(member.getMemberId(), orderItemRequests, postcode, address);

        // 주문을 만들기 전 상품의 재고 수량 저장
        int productStock = productRepository.findById(1L).get().getStockQuantity();
        int productStock2 = productRepository.findById(2L).get().getStockQuantity();

        // when
        orderService.createOrder(orderRequest); // id : 2L

        // 주문 생성 시 재고 수량 변화
        assertThat(productRepository.findById(1L).get().getStockQuantity()).isEqualTo(productStock - 2);
        assertThat(productRepository.findById(2L).get().getStockQuantity()).isEqualTo(productStock2 - 1);

        orderService.cancelOrder(2L);

        Optional<Order> optionalOrder = orderRepository.findById(2L);

        // then
        assertThat(optionalOrder.isPresent()).isTrue();
        assertThat(optionalOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);

        // 주문 취소 시 재고 수량 복구
        assertThat(productRepository.findById(1L).get().getStockQuantity()).isEqualTo(productStock);
        assertThat(productRepository.findById(2L).get().getStockQuantity()).isEqualTo(productStock2);
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
        OrderItem orderItem1 = OrderItem.createOrderItem(product1, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(product2, 2);

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        return Arrays.asList(orderItem1, orderItem2);
    }
}