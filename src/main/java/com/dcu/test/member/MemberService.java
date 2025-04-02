package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 이메일 중복 확인 메서드
    public boolean memberFindByEmailIsPresent(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원가입 메서드
    @Transactional
    public void signUp(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        memberRepository.save(member);
    }

    // 이메일로 사용자 정보 조회
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 회원을 찾을 수 없습니다."));
    }

    // 회원 정보 수정
    @Transactional
    public void updateMember(Member member) {
        memberRepository.save(member);
    }
}
