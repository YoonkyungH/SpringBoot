package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    // ORDINAL이 기본값인데 123 이렇게 들어가서 중간에 다른게 생기면 밀려버리므로 절대 사용 금지
    @Enumerated(EnumType.STRING)    // 꼭 STRING으로 넣어주기
    private DeliveryStatus status;  // READY, COMP
}


