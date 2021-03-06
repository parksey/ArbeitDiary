package com.arbietDiary.arbietdiary.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.arbietDiary.arbietdiary.configuration.model.AuthConstants;
import com.arbietDiary.arbietdiary.configuration.model.TokenUtils;
import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.service.MemberService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	private final MemberService memberService;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
		super(authenticationManager);
		this.memberService = memberService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("[토큰 인증]");
		System.out.println("[토큰 인증] : internal = " +request.getRequestURI());
		System.out.println("[토큰 인증] : ContextHolder = "+SecurityContextHolder.getContext().getAuthentication());
	
		String jwtToken = request.getHeader(AuthConstants.AUTH_HEADER);
		
		System.out.println(request.getHeader(AuthConstants.AUTH_HEADER));
		System.out.println("[토큰 인증] : 헤더토큰 = "+ jwtToken);

		
		if(jwtToken == null || !jwtToken.startsWith(AuthConstants.TOKEN_TYPE)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = jwtToken.replace(AuthConstants.TOKEN_TYPE + " ", "");
		String username = null;
		username = TokenUtils.getUserEmailFromAccessToken(token);
		System.out.println("[토큰 인증] : username = " + username);
		/*
		 * 아래 에러 = 토큰값 완료
		 * io.jsonwebtoken.ExpiredJwtException: JWT expired at
		 */
		if(username != null) {
			CustomUserDetails userDetails = memberService.apiUserDetail(username);
			System.out.println("[토큰 인증: 유저 정보] : "+userDetails);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
