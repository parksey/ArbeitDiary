package com.arbietDiary.arbietdiary.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.arbietDiary.arbietdiary.configuration.JwtAuthenticationFilter;
import com.arbietDiary.arbietdiary.configuration.model.AuthConstants;
import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.arbietDiary.arbietdiary.member.model.MemberTokenDto;
import com.arbietDiary.arbietdiary.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final MemberService memberService;
	
	@ResponseBody
	@PostMapping("/api/userRegist")
	public ResponseEntity<?> userRegist(@RequestBody MemberInput memberInput){
		System.out.println("[API 회원가입] : 요청");
		System.out.println("[API 회원가입] : memberInput = "+memberInput);
		boolean result = memberService.register(memberInput);
		return ResponseEntity.ok().body(result);
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response){
		System.out.println("[API 로그인] : 요청");
		//System.out.println("[API 로그인] : memberIinput = "+memberInput);
		Authentication auth = jwtAuthenticationFilter.attemptAuthentication(request, response);
		System.out.println("[API 로그인] : 엑세스 토큰 생성");
		MemberTokenDto token = memberService.getloginToken((CustomUserDetails)auth.getPrincipal());
		
		boolean result = true;
		if(token == null) {
			result = false;
		}
		
		System.out.println("[API 로그인] : token =" +token);
		System.out.println("[API 로그인] : result = " + result);
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getAccessToken());
		response.addHeader(AuthConstants.RERESH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getRefreshToken());

		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/emailAuth") // get은 requestbody 불가
	public ResponseEntity<?> emailAuth(@RequestBody MemberInput memberInput){
		System.out.println("[이메일 인증] : 요청");
		System.out.println(memberInput);
		boolean result = memberService.emailAuth(memberInput.getId());
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/find/password") // get은 requestbody 불가
	public ResponseEntity<?> findPassword(@RequestBody MemberInput memberInput){
		System.out.println("[API 비밀번호 찾기]");
		System.out.println(memberInput);
		boolean result = false;
		try {
			result = memberService.sendResetPassword(memberInput.getUserId(), memberInput.getUserName());
		} catch(Exception e) {
			System.out.println(e);
		}
		System.out.println(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/reset/password") // get은 requestbody 불가
	public ResponseEntity<?> resetPassword(@RequestBody MemberInput memberInput, HttpServletRequest request){
		System.out.println("[API 비밀번호 찾기]");
		System.out.println(memberInput);
		String uuid = request.getParameter("id");
		boolean result = false;
		try {
			result  =memberService.checkResetPasswordKey(uuid);
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("[API RESULT] : "+result);
		if(result) {
			result = memberService.resetPassword(uuid, memberInput.getUserPassword());
		}
		System.out.println("[API RESULT] : "+result);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/find/userid") // get은 requestbody 불가
	public ResponseEntity<?> findId(@RequestBody MemberInput memberInput, HttpServletRequest request){
		System.out.println("[API 비밀번호 찾기]");
		System.out.println(memberInput);
		List<String> userIdList = memberService.getUserId(memberInput.getUserPhone(), memberInput.getUserName());
		
		return ResponseEntity.ok().body(userIdList);
	}
}
