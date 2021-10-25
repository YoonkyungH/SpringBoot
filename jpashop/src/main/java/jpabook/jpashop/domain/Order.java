package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 생략 가능해짐
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // @XToOne 관계는 기본이 즉시 로딩이므로 모두 찾아 직접 지연로딩으로 설정해야 한다!!!
    @ManyToOne(fetch = LAZY)    // 멤버와 다대일 관계
    @JoinColumn(name = "member_id")
    // 연관관계의 주인
    // 멤버 대신 여기 값을 세팅해야 외래 키 값이 변경됨
    private Member member;

    // cascade=ALL -> orderItems에 저장하고 Order를 저장시키면 orderItems도 같이 저장됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)    // 꼭 STRING으로 넣어주기
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메소드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);         // 주문 상태와
        order.setOrderDate(LocalDateTime.now());    // 주문 시간까지 세팅됨
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
    */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {   // 배송 완료가 되어버리면 취소할 수 없음
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        // if문에 걸리지 않고 통과되면 상태를 취소 상태로 바꿔주면 됨
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
