package com.example.noticeBoard.repository;

import com.example.noticeBoard.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
