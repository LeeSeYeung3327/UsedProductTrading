package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화 추가

    // 회원가입 페이지 이동
    @GetMapping("/memberSignUp")
    public String memberSignUp() {
        return "member/memberSignUp";
    }

    // 회원가입 처리
    @PostMapping("/memberSignUp")
    public String memberSignUp(String email, String password, String passwordConfirm, String name, Model model) {
        try {
            // 이메일 검증
            if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return validationFailed(model, email, name, "유효한 이메일을 입력해주세요.");
            }

            // 이메일 중복 확인
            if (memberService.memberFindByEmailIsPresent(email)) {
                return validationFailed(model, email, name, "이미 등록된 이메일입니다.");
            }

            // 비밀번호 검증
            if (password == null || password.trim().length() < 8) {
                return validationFailed(model, email, name, "비밀번호는 8자 이상이어야 합니다.");
            }

            // 비밀번호 확인 검증
            if (!password.equals(passwordConfirm)) {
                return validationFailed(model, email, name, "비밀번호가 일치하지 않습니다.");
            }

            // 이름 검증
            if (name == null || name.trim().isEmpty()) {
                return validationFailed(model, email, name, "이름을 입력해주세요.");
            }

            // 회원 객체 생성 및 저장
            Member member = new Member();
            member.setEmail(email);
            member.setPassword(password);
            member.setName(name);
            member.setJoinDate(LocalDate.now()); // 가입일 설정

            memberService.signUp(member);
            return "redirect:/memberLogin"; // 회원가입 후 로그인 페이지로 이동

        } catch (Exception e) {
            return validationFailed(model, email, name, "회원가입 중 오류가 발생했습니다.");
        }
    }

    // 마이페이지 이동
    @GetMapping("/memberPage")
    public String memberPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/memberLogin"; // 로그인 안 된 상태라면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMemberByEmail(userDetails.getUsername());
        if (member == null) {
            return "redirect:/memberLogin"; // 사용자 정보가 없으면 로그인 페이지로 이동
        }

        model.addAttribute("member", member);
        return "member/memberPage";
    }

    // 로그인 페이지 이동
    @GetMapping("/memberLogin")
    public String memberLogin() {
        return "member/memberLogin";
    }

    // 유효성 검증 실패 시 처리
    private String validationFailed(Model model, String email, String name, String message) {
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "member/memberSignUp";
    }
}
