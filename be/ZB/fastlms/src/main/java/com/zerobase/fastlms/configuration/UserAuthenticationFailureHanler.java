package com.zerobase.fastlms.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UserAuthenticationFailureHanler extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		boolean result = false;
		System.out.println("로그인 실패");
		System.out.println(exception);
		String msg = null;
		if(exception instanceof InternalAuthenticationServiceException) {
			msg = exception.getMessage();
			result = true;
		}
		else if (exception instanceof UsernameNotFoundException) {
			msg = exception.getMessage();
			System.out.println("UesrnameNotfound");
		}
		else if (exception instanceof BadCredentialsException) {
			msg = "회원 정보 불일치";
			System.out.println("회원 정보 불일치");
		}
		
		setUseForward(true);
		setDefaultFailureUrl("/member/login?error=true");
		request.setAttribute("errorMessage", msg);
		System.out.println("result:"+result);
		request.setAttribute("result", result);
		super.onAuthenticationFailure(request, response, exception);
	}
}
