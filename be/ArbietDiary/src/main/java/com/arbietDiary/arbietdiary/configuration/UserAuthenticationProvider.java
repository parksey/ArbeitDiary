package com.arbietDiary.arbietdiary.configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider{
	private final MemberService memberService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("[Provider: 토큰 발급] : Manager를 통해 Provider 호출");
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		System.out.println("[Provider] : token = "+token);
		
		String userId = token.getName();
		String userPw = token.getCredentials().toString();
		
		System.out.println("[Provider] : userId = "+userId);
		System.out.println("[Provider] : userPw = "+userPw);
	
		CustomUserDetails userDetails = (CustomUserDetails)memberService.loadUserByUsername(userId);
		System.out.println("[Provider] : userDetails = "+userDetails.getUsername());
		
		if(!bCryptPasswordEncoder.matches(userPw, userDetails.getPassword())) {
			System.out.println("[Provider: 비밀번호 오류]");
			throw new BadCredentialsException("Invaild password");
		}
		UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
		System.out.println("[Provider: 새로운 토큰] : token = "+newToken);
		return newToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
