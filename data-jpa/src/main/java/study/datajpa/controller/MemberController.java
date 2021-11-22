package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable) {   // Page는 인터페이스
        // 엔티티를 반환할 경우 반드시 아래와 같이 DTO로 변환해 반환해야 함. 엔티티를 그대로 노출시키는 것은 상당히 위험함
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
        // 실무에서는 위 코드를 인라인으로 처리함. (option + command + N)
    }

    @PostConstruct
    public void init() {    // 멤버 생성하기
        for (int i=0; i<100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }        
    }
}
