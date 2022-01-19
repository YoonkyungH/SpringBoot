package com.example.noticeBoard.service;

import com.example.noticeBoard.domain.member.Member;
import com.example.noticeBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member searchMemberById(Long id) {
        Member entity = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다.")
        );

        return new Member()
    }
}
