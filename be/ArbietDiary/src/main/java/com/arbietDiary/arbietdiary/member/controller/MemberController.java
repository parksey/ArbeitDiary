package com.arbietDiary.arbietdiary.member.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.arbietDiary.arbietdiary.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping("/member/register")
	public String register(){
		System.out.println("[회원 가입] : 화면");
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String submitRegist(Model model, MemberInput memberInput) {
		System.out.println("[회원 가입] : 요청");
		System.out.println("[회원 가입] : member = "+memberInput);
		
		boolean result = memberService.register(memberInput);
		model.addAttribute("result", result);
		return "member/register-complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model, HttpServletRequest request) {
		System.out.println("[이메일 인증]");
		String uuid = request.getParameter("id");
		System.out.println(uuid);
		
		boolean result = memberService.emailAuth(uuid);
		model.addAttribute("result", result);
		return "member/email-auth";
	}
	
	@GetMapping("/member/info")
	public String memberInfo(Model model, Principal principal) {
		System.out.println("[사용자 정보]");
		return "member/info";
	}
	
	@GetMapping("/member/login")
	public String memberLogin(HttpServletRequest request) {
		System.out.println("[로그인 화면] : 화면");
		return "member/login";
	}
	
	@PostMapping("/member/login")
	public String memberLoginSubmit(HttpServletRequest request, MemberInput memberInput) {
		System.out.println("[로그인 화면] : 요청");
		System.out.println(memberInput);

		return "member/login";
	}
	
	@GetMapping("/member/find/password")
	public String findPassword() {
		System.out.println("[비밀번호 찾기] : 화면");
		return "member/find-password";
	}
	
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(MemberInput memberInput, Model model) {
		System.out.println("[비밀번호 찾기] : 요청");
		boolean result = false;
		try {
			result = memberService.sendResetPassword(memberInput);
		} catch(Exception e) {
			System.out.println(e);
		}
		
		model.addAttribute("result", result);	
		return "member/find-password-result";
	}
	
	@GetMapping("/member/reset/password")
	public String resetPassword(Model model, HttpServletRequest request) {
		System.out.println("[비밀번호 초기화] : 화면");
		
		String uuid = request.getParameter("id");
		boolean result = memberService.checkResetPasswordKey(uuid);
		
		model.addAttribute("result", result);
		return "member/reset-password";
	}
	
	@PostMapping("/member/reset/password")
	public String resetPaswwordSubmit(Model model, MemberInput memberInput) {
		System.out.println("[비밀번호 초기화] : 화면");
		System.out.println("[비밀번호 초기화] : memberInput = "+memberInput);
		boolean result = false;
		try {
			result = memberService.resetPassword(memberInput.getId(), memberInput.getUserPassword());
		}catch(Exception e) {
			System.out.println("Hello");
		}
		System.out.println(result);
		model.addAttribute("result", result);
		return "member/reset-password-result";
	}
	
	@GetMapping("/member/password")
	public String memberPassword() {
		System.out.println("[비밀번호 변경] : 화면");
		return "member/password";
	}
	
	@PostMapping("/member/password")
	public String submitMemberPassword(Model model, MemberInput memberInput, Principal principal) {
		System.out.println("[비밀번호 변경] : 요청");
		String userId = principal.getName();
		System.out.println(memberInput);
		memberInput.setUserId(userId);
		boolean result = memberService.updateMemberPassword(memberInput);
	
		return "member/password";
	}
	
	@GetMapping("/member/find/email")
	public String findEmail() {
		System.out.println("[아이디 찾기]");
		return "member/find-email";
	}
}
