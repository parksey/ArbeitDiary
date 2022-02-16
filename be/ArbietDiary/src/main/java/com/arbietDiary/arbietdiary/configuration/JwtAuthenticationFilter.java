package com.arbietDiary.arbietdiary.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arbietDiary.arbietdiary.error.AuthenticationUserInputException;
import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private static boolean postOnly = true; 
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		// TODO Auto-generated constructor stub
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException { 
		System.out.println("[Security : Authentication]");
		if(postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: "+request.getMethod());
		}
		
		UsernamePasswordAuthenticationToken authRequest = null;
		
		try {
			MemberInput member = new MemberInput();
			System.out.println("[Authentication: ContentType] : "+request.getContentType());
			// application/x-www-form-urlencoded
			if(request.getContentType().equals("application/x-www-form-urlencoded")) {
				member.setUserId(obtainUsername(request)); 
				member.setUserPassword(obtainPassword(request));
			} 
			else if(request.getContentType().equals("application/json")) {
				ObjectMapper objectMapper = new ObjectMapper();
				member = objectMapper.readValue(request.getInputStream(), MemberInput.class);	
			}
			System.out.println("[Authentication: Input] : member = "+member);
			if(member.getUserId() == null) { member.setUserId("");} 
			if(member.getUserPassword() == null) { member.setUserPassword("");}
			authRequest = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getUserPassword());
		} catch (Exception e) {
			throw new AuthenticationUserInputException("토큰 오류 : "+e);
		}
		setDetails(request, authRequest); 
		System.out.println("====================================");
		System.out.println("[Authentication Info]");
		System.out.println("Details : "+authRequest.getDetails());
		System.out.println("Authori : "+authRequest.getAuthorities());
		System.out.println("Credent : "+authRequest.getCredentials());
		System.out.println("Names   : "+authRequest.getName());
		System.out.println("princip : "+authRequest.getPrincipal());
		System.out.println(authRequest);
		System.out.println("Manager호출");
		Authentication authentication = getAuthenticationManager().authenticate(authRequest);
		System.out.println("newToken성공 : Manager 끝");
		System.out.println("====================================");
		System.out.println("authentication : "+authentication);
		CustomUserDetails detail = (CustomUserDetails)authentication.getPrincipal();
		System.out.println("Principal : " +detail.getMember());
		System.out.println("Principal : " +detail.getAuthorities());
		System.out.println("비번(보이면X): "  +authentication.getCredentials());
		System.out.println("세부사항     : " +authentication.getDetails());
		System.out.println("이름        : " +authentication.getName());
		System.out.println("권한        : " +authentication.getAuthorities());
		System.out.println("===================================="); 
		System.out.println(authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("[Authentication 완료] : 인증 객체 = "+SecurityContextHolder.getContext());
		
		/*
		 * Session영역 자동 저장
		 */
		return authentication;
	}

}
