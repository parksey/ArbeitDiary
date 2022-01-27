package com.zerobase.fastlms.member.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.exception.MemberNotEmailAllthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final MailComponent mailComponents;
	private final MemberMapper memberMapper;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository, MailComponent mailComponents, MemberMapper memberMapper) {
		this.memberRepository = memberRepository;
		this.mailComponents = mailComponents;
		this.memberMapper = memberMapper;
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
				.userState(Member.MEMBER_STATUS_YET)
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
		member.setUserState(Member.MEMBER_STATUS_ING);
		System.out.println("<member>:"+member);
		memberRepository.save(member);
		
		if(!member.isEmailAuthYn()) {
			return false;
		}
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
		
		if(Member.MEMBER_STATUS_YET.equals(member.getUserState())) {
			String uuid = member.getEmailAuthKey();
			String email = member.getUserId();
			String subject = "재이메일 인증 안내입니다.";
			String text = "<h1>이메일 인증안내입니다.</h1><p>아래 링크를 클릭하여 가입을 완료하세요</p>"
					+"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>가입 완료</a>하러가기</div>";
			mailComponents.sendMail(email, subject, text);
			throw new MemberNotEmailAllthException("이메일 활성화 이후 로그인 해주세요.");
		}
		
		if(Member.MEMBER_STATUS_BLACK.equals(member.getUserState())) {
			throw new MemberStopUserException("정지된 회원 입니다.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if(member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		System.out.println("impl끝");
		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		return sendEmailData(optionalMember);
	}
	
	private boolean sendEmailData(Optional<Member> optionalMember) {
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
		return true;
	}
	
	@Override
	public boolean resetPassword(String id, String password) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(id);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("잘못된 경로 입니다.");
		}
		
		Member member = optionalMember.get();
		
		if(member.getResetPasswordKey() == null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		if(member.getResetPasswordDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효 시간이 지났습니다.");
		}
		
		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encPassword);
		member.setResetPasswordKey("");
		member.setResetPasswordDt(null);
		memberRepository.save(member);
		
		return true;
	}
	
	@Override
	public boolean checkResetPasswordKey(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("check잘못된 경로 입니다.");
		}
		
		Member member = optionalMember.get();
		if(member.getResetPasswordKey() == null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		if(member.getResetPasswordDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효 시간이 지났습니다.");
		}
		
		return true;
	}
	
	@Override
	public List<MemberDto> list(MemberParam memberParm){
		List<MemberDto> list =  memberMapper.selectList(memberParm);
		System.out.println(memberParm.getSearchType());
		System.out.println(memberParm.getSearchValue());
		System.out.println("list : "+list);
		long totalCount = memberMapper.selectListCount(memberParm);
		System.out.println("totalCount"+totalCount);
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(MemberDto x : list) {
				x.setTotalCount(totalCount);
				x.setMemberNum(totalCount - memberParm.getPageStart() - i);
				i++;
				System.out.println(x);
			}
		}
		return list;	
	}
	
	@Override
	public MemberDto detail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		
		if(!optionalMember.isPresent()) {
			return null;
		}
		Member member = optionalMember.get();
		System.out.println(member);
		MemberDto memberDto = MemberDto.of(member);
		System.out.println(memberDto);
		return memberDto;
	}
	
	@Override
	public boolean updateStatus(String userId, String userStatus) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		System.out.println(userId+":"+userStatus);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보 존재하지 않습니다.");
		}
		Member member = optionalMember.get();
		member.setUserState(userStatus);
		memberRepository.save(member);
		
		return true;
	}
	
	@Override
	public boolean adminSendPasswordEmail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
	
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보 존재하지 않습니다.");
		}
		
		return sendEmailData(optionalMember);
	}
}
