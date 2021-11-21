package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
/*
항상 사용자 정의 리포지토리가 필요한 것은 아니므로 이렇게 임의의 리포지토리를 만들어 사용해도 된다.
(이렇게 인터페이스가 아닌 클래스로 만들어 스프링빈으로 등록해 직접 사용해도 된다. 이 경우 스프링 데이터 JPA와는 별개로 동작함)
 */
public class MemberQueryRepository {

    private final EntityManager em;

    List<Member> findAllMembers() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
