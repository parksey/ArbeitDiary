package com.zerobase.fastlms.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class ApiMemberController {
	private final MemberService memberService;	
	
	@ResponseBody
	@GetMapping("/api/users")
	public ResponseEntity<?>  user (Model model, MemberInput memberInput) {
		// 요청을 보낸 클라이언트의 IP주소를 반환합니다.
		boolean result = memberService.register(memberInput);
		System.out.println(memberInput);
		return ResponseEntity.ok().body(memberInput);
	}
	
	@PostMapping("/api/hello")
	public String hello() {
		return "안녕";
	}
	
	
}
