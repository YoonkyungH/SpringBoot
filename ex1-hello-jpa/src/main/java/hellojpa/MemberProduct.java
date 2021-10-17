package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {     // 중간 테이블을 엔티티로 승격

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


    // 이런식으로 원하는 걸 넣어서 사용할 수 있게 됨
    private int count;
    private int price;
    private LocalDateTime orderDateTime;
}
