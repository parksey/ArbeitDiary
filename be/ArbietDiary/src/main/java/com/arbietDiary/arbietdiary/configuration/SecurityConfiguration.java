package com.arbietDiary.arbietdiary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.arbietDiary.arbietdiary.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private final MemberService memberService;
	@Bean
	PasswordEncoder getPasswordEncoder() {
		System.out.println("[Bean등록 : PasswordEncoder]");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		System.out.println("[Bean등록 : JwtAuthenticationFilter]");
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		jwtAuthenticationFilter.setUsernameParameter("userId");
		jwtAuthenticationFilter.setPasswordParameter("userPassword");
		return jwtAuthenticationFilter;
	}
	
	@Bean
	public UserAuthenticationProvider userAuthenticationProvider() {
		System.out.println("[Bean등록 : UserAuthenticationProvider]");
		return new UserAuthenticationProvider(memberService, bCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		System.out.println("[Bean등록 : BCryptPasswordEncoder]");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserAuthenticationSuccessHandler authenticationSuccessHandler() {
		System.out.println("[Bean등록 : UserAuthenticationSuccessHandler]");
		return new UserAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		
		// JWT 토큰용
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.formLogin()
				.loginPage("/member/login")
				.usernameParameter("userId")
				.passwordParameter("userPassword")
				.successHandler(authenticationSuccessHandler())
				.permitAll()
			.and()
			.httpBasic().disable() // httpBasic 방식 사용 X
			.addFilter(jwtAuthenticationFilter())
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), memberService));
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true); // 로그아웃시 모두 삭제
		
		http.authorizeRequests()
			.antMatchers("/"
					,"/member/login"
					,"/member/register"
					,"/member/email-auth"
					,"/member/find/password"
					,"/member/find/email"
					,"/member/reset/password"
					,"/api/login")
			.permitAll();
			
		http.authorizeRequests()
			.antMatchers("/member/info")
			.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");
		
		super.configure(http);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("[초기 설정: Config-AuthenticationMangerBuilder]");
		auth.authenticationProvider(userAuthenticationProvider());
	}
}