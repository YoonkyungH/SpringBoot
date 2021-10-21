package jpql;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS") // 관례때문에 name 지정해주기
public class Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
