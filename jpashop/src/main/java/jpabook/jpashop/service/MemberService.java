package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
//import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service    // 자동으로 스프링빈으로 등록돼 관리됨
@Transactional(readOnly = true) // 기본적으로 조회만 하는 건 readOnly=true로 두는 것이 좋음
@RequiredArgsConstructor    // final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

//    // 생성자 인젝션 방법. 스프링이 뜰 때 생성자에서 인젝션 해줌(이후 변경될 위험이 사라짐)
//    // 생성자가 지금처럼 딱 하나만 있는 경우에는 스프링이 자동으로 인젝션 해줘서 어노테이션 생략해도 됨
//    // @RequiredArgsConstructor가 있어서 해당 생성자는 필요없어짐
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional  // 단순 조회만 하는 것이 아니기 때문에 readOnly = true를 하면 안됨 (옵션을 안 주면 기본 값은 false)
    public Long join(Member member) {

        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    // 중복 회원이면 예외를 터뜨릴 것
    // (실무에서는 동시에 두 고객이 가입할 때를 또 잡아주는 것이 좋다.)
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     *
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {  // 단건조회
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
