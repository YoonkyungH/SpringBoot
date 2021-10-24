package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue // sequence 값 사용
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    // 연관관계의 주인이 아니기 때문에 값을 넣는다고 외래키 값이 변경되진 않음
    private List<Order> orders = new ArrayList<>();

}
