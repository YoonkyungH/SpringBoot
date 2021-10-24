package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
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

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//
//        order.setMember(member);
//    }

}
