package com.example.application.domain.member.svc;

import com.example.application.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  // 가입
  Member join(Member member);

  // 회원 존재 유무
  boolean isMember(String email);

  // 회원 조회
  Optional<Member> findByMemberNo(Long memberNo);
  Optional<Member> findByEmail(String email);
}
