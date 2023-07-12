package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.item.Book;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void goodsOrder() {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        Assertions.assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice());
        Assertions.assertThat(8).isEqualTo(book.getStockQuantity());
    }

    @Test
    @DisplayName("상품주문 재고수량 초과")
    void orderQuantityExceeded() {
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //when

        //then
        Assertions.assertThatThrownBy( () -> orderService.order(member.getId(), member.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    @DisplayName("주문 취소")
    void OrderCancel() {
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(10).isEqualTo(book.getStockQuantity());
    }

    @Test
    @DisplayName("재고수량 초과")
    void quantityExceeded() {
        //given

        //when

        //then

    }


    private Member createMember() {
        Member member = new Member();
        member.setUsername("member1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}