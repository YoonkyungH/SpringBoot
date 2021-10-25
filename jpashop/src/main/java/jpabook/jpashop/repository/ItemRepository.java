package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {  // id값이 없다는 것은 완전히 새로 생성한 객체라는 것
            em.persist(item);       // 그래서 신규 등록
        } else {
            em.merge(item); // merge: 업데이트 비슷한 것(진짜 update는 아님)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {   // 여러개 찾는 건 jpql 필요
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
