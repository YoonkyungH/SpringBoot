package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 컴포넌트 스캔에 의해 자동으로 스프링 빈으로 관리가 됨
@RequiredArgsConstructor    // 스프링이 PersistenceContext 대신 Autowired로도 인젝션될 수 있도록 지원함. 그래서 Autowired니까 RequiredArgsConstructor로 코드를 줄일 수 있음
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 이때 영속성 컨텍스트에 객체를 올림
    }

    public Member findOne(Long id) {    // 단건조회
        return em.find(Member.class, id);   // (타입, pk)
    }

    public List<Member> findAll() {
        // option + command + N: Inline 단축키
        // sql: 테이블을 대상으로 쿼리
        // jpql: 엔티티를 대상으로 쿼리
        return em.createQuery("select m from Member m", Member.class)  // (쿼리, 반환타입)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
