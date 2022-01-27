package com.zerobase.fastlms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.zerobase.fastlms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private final MemberService memberService;
	
	@Bean
	PasswordEncoder getPasswodEncoder() {
		System.out.println("Bean getPass");
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserAuthenticationFailureHanler getFailureHandler() {
		System.out.println("Bean User");
		return new UserAuthenticationFailureHanler();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		System.out.println("configure");
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		// 주소 URL 권한 설정
		http.authorizeRequests()
			.antMatchers("/"
				,"/member/register"
				,"/member/email-auth"
				,"/member/find/password"
				,"/member/find/email"
				,"/member/reset/password"
				,"/api/**")
			.permitAll();

		http.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority("ROLE_ADMIN");
		
		http.formLogin()
			.loginPage("/member/login")
			.failureHandler(getFailureHandler())//로그인 실패시 처리
			.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true);
		
		http.exceptionHandling()
			.accessDeniedPage("/error/denied");
		
		super.configure(http);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("configue - auth");
		auth.userDetailsService(memberService)
			.passwordEncoder(getPasswodEncoder());
		
	}
	

}
