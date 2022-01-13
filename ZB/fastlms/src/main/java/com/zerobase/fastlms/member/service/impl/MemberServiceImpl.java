package com.zerobase.fastlms.member.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.exception.MemberNotEmailAllthException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final MailComponent mailComponents;
	
	public MemberServiceImpl(MemberRepository memberRepository, MailComponent mailComponents) {
		this.memberRepository = memberRepository;
		this.mailComponents = mailComponents;
	}
	
	@Override
	public boolean register(MemberInput parameter) {
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		System.out.println("회원 정보 찾는 중");
		if(optionalMember.isPresent()) {
			System.out.println("존재함");
			return false;
		}
		System.out.println("존재하지 않음");
		String encPassword = BCrypt.hashpw(parameter.getUserPassword(), BCrypt.gensalt());
		
		String uuid = UUID.randomUUID().toString();
		Member member = Member.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.password(encPassword)
				.phone(parameter.getUserPhone())
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.build();
		
		memberRepository.save(member);
		
		String email = member.getUserId();
		String subject = "가입에 축하드립니다.";
		String text = "<h1>가입에 축하드립니다</h1><p>아래 링크를 클릭하여 가입을 완료하세요</p>"
				+"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>가입 완료</a>하러가기</div>";
		mailComponents.sendMail(email, subject, text);
		System.out.println(true);
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		System.out.println("optionalMember : "+optionalMember.get());
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		
		memberRepository.save(member);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUserName");
		
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			System.out.println("loadUserByUserName - isNotParent");
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		System.out.println("loadUserByUserName - isParent");
		Member member = optionalMember.get();
		
		if(!member.isEmailAuthYn()) {
			throw new MemberNotEmailAllthException("이메일 활성화 이후 로그인 해주세요.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		System.out.println("impl끝");
		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		String uuid = UUID.randomUUID().toString();
		Member member = optionalMember.get();
		
		member.setResetPasswordKey(uuid);
		member.setResetPasswordDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String email = member.getUserId();
		String subject = "비밀번호 변경 안내입니다.";
		String text = "<h1>비밀번호 변경 안내입니다.</h1><p>아래 링크를 클릭하여 비밀번호를 변경하세요</p>"
				+"<div><a href='http://localhost:8080/member/reset/password?id="+uuid+"'>비밀번호 변경</a>하러가기</div>";
		mailComponents.sendMail(email, subject, text);
		System.out.println(true);
		
		return true;
	}
	
	@Override
	public boolean resetPassword(ResetPasswordInput parameter) {
		//Optional<Member> optionalMember = memberRepository.findByUUID(parameter.getId());
		return false;
	}
}
