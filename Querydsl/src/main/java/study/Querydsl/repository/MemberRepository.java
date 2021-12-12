package study.Querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.Querydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    // select m from Member m where m.username = ?
    List<Member> findByUsername(String username);
}
