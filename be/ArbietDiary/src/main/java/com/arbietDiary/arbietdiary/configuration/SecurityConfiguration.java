package com.arbietDiary.arbietdiary.configuration;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.arbietDiary.arbietdiary.configuration.model.AuthConstants;
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
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
        		List.of(
        		"http://54.180.70.202"
        		,"http://localhost:3000"
        		,"http://localhost:8080"
        				));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of(
        		HttpMethod.POST.name(), 
        		HttpMethod.GET.name(), 
        		HttpMethod.OPTIONS.name()
        		));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader(AuthConstants.AUTH_HEADER);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		
		// JWT 토큰용
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.cors().configurationSource(corsConfigurationSource());
		http.formLogin()
				.loginPage("/index.html")
//				.loginPage("/member/login")
//				.usernameParameter("userId")
//				.passwordParameter("userPassword")
//				.successHandler(authenticationSuccessHandler())
				//.permitAll()
			.and()
			.httpBasic().authenticationEntryPoint(new NoPopupBasicAuthenticatioinEntryPoint())
			.and()
			.addFilter(jwtAuthenticationFilter())
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), memberService));
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true); // 로그아웃시 모두 삭제
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll();
		
		/*
		 * 개발용
		 */
		http.authorizeRequests()
			.antMatchers("/dev"
					,"/dev/member/login"
					,"/dev/member/register"
					,"/dev/member/email-auth"
					,"/dev/member/find/password"
					,"/dev/member/find/email"
					,"/dev/member/reset/password"
					,"/dev/api/login")
			.permitAll();
		
		http.authorizeRequests()
		.antMatchers("/dev/member/info")
		.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");
		
		/*
		 * 배포 with React
		 */
		http.authorizeRequests()
			.antMatchers("/"
					,"/done"
					,"/login"
					,"/regist"
					,"/findpassword"
					,"/findid"
					,"/member/reset/password")
			.permitAll();
		
		http.authorizeRequests()
		.antMatchers("/api/userRegist"
				,"/api/login"
				,"/api/emailAuth"
				,"/api/find/password"
				,"/api/reset/password"
				,"/api/find/userid"
				)
		.permitAll();
		
		
		super.configure(http);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("[초기 설정: Config-AuthenticationMangerBuilder]");
		auth.authenticationProvider(userAuthenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(
                "/index.html", 
                "/favicon.ico",   
                "/*.json",
                "/*.png",
				"/static/**"
         );
	    			 
	}
}
