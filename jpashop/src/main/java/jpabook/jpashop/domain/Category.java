package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    // 객체는 컬렉션이 있어 다대다 관계가 가능한데 관계형 db는 컬렉션 관계를 양쪽에 가질 수 없으므로
    // 일대다, 다대일로 중간에 풀어주는 테이블이 있어야 함
    private List<Item> items = new ArrayList<>();

    /**
     * 셀프로 양방향 연관관계를 집어넣음
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


}
