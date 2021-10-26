package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue // sequence 값 사용
    @Column(name = "member_id")
    private Long id;

    @NotEmpty   // 무조건 값이 있어야 한다는 조건
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore // 주문 정보는 빼놓고 순수하게 회원 정보면 얻을 수 있음
    @OneToMany(mappedBy = "member")
    // 연관관계의 주인이 아니기 때문에 값을 넣는다고 외래키 값이 변경되진 않음
    private List<Order> orders = new ArrayList<>();

}
