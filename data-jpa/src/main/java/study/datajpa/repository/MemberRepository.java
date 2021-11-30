package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    /**
     * Named Query
     */
//    @Query(name = "Member.findByUsername")    // 생략 가능
    List<Member> findByUsername(@Param("username") String username);

    /**
     * 이름없는 named query -> Query
     */
    // JPQL을 다른데가 아닌 인터페이스 메소드에 바로 작성할 수 있음
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // new는 객체 생성하듯(JPQL 문법)
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // 스프링 데이터 JPA는 유연한 반환 타입 지원
//    List<Member> findByListUsername(String username);   // 컬렉션
//    Member findMemberByUsername(String username);   // 단건
//    Optional<Member> findOptionalByUsername(String username);   // 단건 Optional을 반환하는 경우

    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    /**
     * 공통 메소드 오버라이드
     */
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    /**
     * JPQL + 엔티티그래프
     */
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    /**
     * 메소드 이름으로 쿼리에서 특히 편리
     */
//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}

