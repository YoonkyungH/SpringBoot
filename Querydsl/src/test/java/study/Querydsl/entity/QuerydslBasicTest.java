package study.Querydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.Querydsl.entity.QMember.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        // member1 찾기
        String qlString = "select m from Member m " +
                "where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        QMember m = new QMember("m");   // 어떤 QMember인지 구분하는 이름을 줌

        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))   // 파라미터 바인딩 처리
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    // 기본 검색 쿼리
    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))    // 이름이 member1이면서 나이가 10살인 사람을 조회해라
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchAndParam() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where( // 여기 안에서 여러개를 넘기면 걔네가 다 .and로 엮임
                        member.username.eq("member1"),
                        member.age.eq(10)
                )    // 이름이 member1이면서 나이가 10살인 사람을 조회해라
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void resultFetch() {
//        List<Member> fetch = queryFactory
//                .selectFrom(member)
//                .fetch();
//
//        Member fetchOne = queryFactory
//                .selectFrom(QMember.member)
//                .fetchOne();    // 단건조회
//
//        Member fetchFirst = queryFactory
//                .selectFrom(member)
//                .fetchFirst();  // limit(1) + fetchOne()

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();    // 페이징 정보 포함, total count 쿼리 추가 실행

        results.getTotal();
        List<Member> content = results.getResults();

        long total = queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올람차순(asc)
     * 단, 2에서 회원 이름이 없으면 마지막에 출력(null last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    public void paging1() { // offset, limit로 페이징을 지원
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)  // 0부터 시작(zero index)
                .limit(2)   // 최대 2건 조회
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void paging2() { // 전체 조회 수가 필요할 때
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();    // 페이징 쿼리 정보 포함. total count 쿼리 추가 실행

        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }
}
