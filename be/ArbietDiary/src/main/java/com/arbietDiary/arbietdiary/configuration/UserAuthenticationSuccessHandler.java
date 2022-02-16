package com.arbietDiary.arbietdiary.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.arbietDiary.arbietdiary.configuration.model.AuthConstants;
import com.arbietDiary.arbietdiary.configuration.model.TokenUtils;
import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;

public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws ServletException, IOException {
		System.out.println("[일반적으로 되는지 실험]");
		System.out.println("[JWT TOKEN 생성]");
		System.out.println("[JWT EX: AUTH]"+ authentication);
		
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		String accessToken = TokenUtils.generateAccessToken(user);
		String refreshToken = TokenUtils.generateRefreshToken(user);

		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
		response.addHeader(AuthConstants.RERESH_HEADER, AuthConstants.TOKEN_TYPE + " " + refreshToken);
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		System.out.println("[JWT EX: ContextHolder] : "+ SecurityContextHolder.getContext());
		System.out.println("[JWT EX: Auth] : "+ SecurityContextHolder.getContext().getAuthentication());
		clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, "/");		
	}
}
