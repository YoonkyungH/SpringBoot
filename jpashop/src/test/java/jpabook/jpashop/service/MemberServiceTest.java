package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
//    @Autowired EntityManager em;

    @Test
//    @Rollback(false)    // 이게 있어야 insert 쿼리가 보임. 영속성 컨텍스트 플러시. (아니면 em.flush())
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Ha");

        // when
        Long savedId = memberService.join(member);

        // then
//        em.flush(); // 영속성 컨텍스트에 있는 변경된 내용들을 데이터베이스에 반영
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Ha");

        Member member2 = new Member();
        member2.setName("Ha");

        // when
        memberService.join(member1);
        memberService.join(member2);    // 여기서 예외가 발생해야 함


        // then
        // fail(): 코드가 돌다가 여기로 오면 안되는 것
        fail("예외가 발생해야 한다.");
    }
}