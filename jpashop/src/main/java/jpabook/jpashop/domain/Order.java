package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne    // 멤버와 다대일 관계
    @JoinColumn(name = "member_id")
    // 연관관계의 주인
    // 멤버 대신 여기 값을 세팅해야 외래 키 값이 변경됨
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)    // 꼭 STRING으로 넣어주기
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

}
