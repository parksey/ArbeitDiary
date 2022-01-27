package com.zerobase.fastlms.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.service.MemberService;

@Controller
public class MemberController {
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/member/register")
	public String register() {
		System.out.println("회원가입GET");
		
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String registerSubmit(Model model, MemberInput parameter) {
		System.out.println("회원가입POST");
		System.out.println(parameter.toString());
		
		boolean result = memberService.register(parameter);
		model.addAttribute("result", result);
		return "member/register-complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model, HttpServletRequest request) {
		String uuid = request.getParameter("id");
		System.out.println(uuid);
		boolean result = memberService.emailAuth(uuid);
		
		model.addAttribute("result", result);
		return "member/email-auth";
	}
	
	@GetMapping("/member/info")
	public String memberInfo() {
		System.out.println("정보");
		return "member/info";
	}
	
	@PostMapping("/member/login")
	public String memberLoginSubmit() {
		System.out.println("Post로그인");
		return "member/login";
	}
	@GetMapping("/member/login")
	public String memberLogin(HttpServletRequest request) {
		System.out.println("Get로그인");
		return "member/login";
	}
	
	@GetMapping("/member/find/password")
	public String findPassword() {
		System.out.println("비밀번호 찾기");
		return "member/find-password";
	}
	
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(ResetPasswordInput parameter, Model model) {
		boolean result = false;
		try {
			result = memberService.sendResetPassword(parameter);
		} catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println(parameter);
		model.addAttribute("result", result);	
		return "member/find-password-result";
	}
	
	@GetMapping("/member/reset/password")
	public String resetPassword(Model model, HttpServletRequest request,ResetPasswordInput parameter) {
		System.out.println("리셋 패스워드");
		System.out.println(parameter);
		
		String uuid = request.getParameter("id");
		boolean result = memberService.checkResetPasswordKey(uuid);
		
		model.addAttribute("result", result);
		return "member/reset-password";
	}
	
	@PostMapping("/member/reset/password")
	public String resetPaswwordSubmit(Model model, ResetPasswordInput parameter) {
		System.out.println("reset post:"+parameter);
		boolean result = false;
		try {
			result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
		}catch(Exception e) {
			System.out.println("Hello");
		}
		System.out.println(result);
		model.addAttribute("result", result);
		return "member/reset-password-result";
	}
	
	@GetMapping("/member/find/email")
	public String findEmail() {
		System.out.println("아이디 찾기");
		return "member/find-email";
	}

	@GetMapping("/projects/newProject")
	public String makeProject() {
		return "/projects/makeProject";
	}
	
	@PostMapping("/projects/newProject")
	public String newProject() {
		return "/projects/project";
	}
	
	@GetMapping("/projects/project")
	public String exsistProject() {
		return "/projects/project";
	}	
}
