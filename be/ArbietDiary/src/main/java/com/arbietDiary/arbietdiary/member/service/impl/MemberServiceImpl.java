package com.arbietDiary.arbietdiary.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.component.MailComponent;
import com.arbietDiary.arbietdiary.configuration.model.TokenUtils;
import com.arbietDiary.arbietdiary.error.MemberNotEmailAllthException;
import com.arbietDiary.arbietdiary.error.MemberStopUserException;
import com.arbietDiary.arbietdiary.member.entity.Member;
import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.arbietDiary.arbietdiary.member.model.MemberRole;
import com.arbietDiary.arbietdiary.member.model.MemberTokenDto;
import com.arbietDiary.arbietdiary.member.repository.MemberRepository;
import com.arbietDiary.arbietdiary.member.service.MemberService;
import com.arbietDiary.arbietdiary.secret.SecretData;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	
	private final MailComponent mailComponent;
	
	@Override
	public boolean register(MemberInput memberInput) {
		Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());
		if(optionalMember.isPresent()) {
			System.out.println("[회원 등록] : 이미 존재");
			return false;
		}
		System.out.println("[회원 등록] : 시작");
		String encodePasswor = BCrypt.hashpw(memberInput.getUserPassword(), BCrypt.gensalt());

		String uuid = UUID.randomUUID().toString();
		Member member = Member.builder()
				.userId(memberInput.getUserId())
				.userName(memberInput.getUserName())
				.password(encodePasswor)
				.phone(memberInput.getUserPhone())
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.userState(Member.MEMBER_STATUS_YET)
				.roles(Member.MEMBER_STATUS_INVALID)
				.refreshToken(Member.MEMBER_STATUS_INVALID)
				.build();
		
		memberRepository.save(member);
		System.out.println("[회원 등록] : 성공");
		
		String email = member.getUserId();
		String subject = "가입에 축하드립니다.";
		String text = "<h1>가입에 축하드립니다</h1><p>아래 링크를 클릭하여 가입을 완료하세요</p>"
				+"<div><a href='"+SecretData.localUrl+"/done?id="+uuid+"'>가입 완료</a>하러가기</div>";
		
		mailComponent.sendMail(email, subject, text);
		System.out.println("[메일 전송] : 성공");
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {
		System.out.println("[이메일 인증]");
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if(!optionalMember.isPresent()) {
			System.out.println("[이메일 인증] : 해당 키가 없습니다.");
			return false;
		}
		
		Member member = optionalMember.get();
		System.out.println("[이메일 인증] : member = "+ member);
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		member.setUserState(Member.MEMBER_STATUS_ING);
		memberRepository.save(member);
		
		return true;
	}
	
	@Override
	public boolean sendResetPassword(String userId, String userName) {
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(userId, userName);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		return sendEmailData(optionalMember);
	}
	
	private boolean sendEmailData(Optional<Member> optionalMember) {
		System.out.println("[비밀번호 찾기] : 이메일 전송");
		String uuid = UUID.randomUUID().toString();
		Member member = optionalMember.get();
		
		member.setResetPasswordKey(uuid);
		member.setResetPasswordDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String email = member.getUserId();
		String subject = "비밀번호 변경 안내입니다.";
		String text = "<h1>비밀번호 변경 안내입니다.</h1><p>아래 링크를 클릭하여 비밀번호를 변경하세요</p>"
				+"<div><a href='"+SecretData.localUrl+"/member/reset/password?id="+uuid+"'>비밀번호 변경</a>하러가기</div>";
		mailComponent.sendMail(email, subject, text);
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
		
		String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encodePassword);
		member.setResetPasswordKey("");
		member.setResetPasswordDt(null);
		memberRepository.save(member);
		System.out.println("[비밀번호 초기화] : 변경 완료");
		return true;
	}
	
	@Override
	public boolean updateMemberPassword(MemberInput memberInput) {
		// TODO Auto-generated method stub
		Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		
		if(!BCrypt.checkpw(memberInput.getUserPassword(), member.getPassword())) {
			return false;
		}
		System.out.println("[비밀번호 변경] : 확인 완료");
		String encPassword = BCrypt.hashpw(memberInput.getNewPassword(), BCrypt.gensalt());
		member.setPassword(encPassword);
		memberRepository.save(member);
		System.out.println("[비밀번호 변경] : 완료");
		return true;
	}
	
	@Override
	public CustomUserDetails loadUserByUsername(String userId) {
		System.out.println("[로그인 시도] : MemberServiceImpl");
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(optionalMember.isEmpty()) {
			System.out.println("[로그인 시도] : 에러, 회원 정보 존재 X");
			throw new UsernameNotFoundException("회원정보 존재 X");
		}
		
		Member member = optionalMember.get();
		
		// 이메일 인증 못함
		if(Member.MEMBER_STATUS_YET.equals(member.getUserState())) {
			String uuid = member.getEmailAuthKey();
			String email= member.getUserId();
			String subject = "재이메일 인증 안내입니다.";
			String text = "<h1>이메일 인증안내입니다.</h1><p>아래 링크를 클릭하여 가입을 완료하세요</p>"
					+"<div><a href='"+SecretData.localUrl+"/done?id="+uuid+"'>가입 완료</a>하러가기</div>";
			mailComponent.sendMail(email, subject, text);
			throw new MemberNotEmailAllthException("이메일 활성화 이후 로그인 해주세요.");
		}
		
		if(Member.MEMBER_STATUS_BLACK.equals(member.getUserState())) {
			throw new MemberStopUserException("정지된 회원 입니다.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER_ROLE_USER));
		if(member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER_ROLE_ADMIN));
		}
		member.setRole(grantedAuthorities);
		memberRepository.save(member);
		System.out.println("[로그인 인증] : 완료");
		return new CustomUserDetails(member, grantedAuthorities);
	}
	
	@Override
	public CustomUserDetails apiUserDetail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		
		if(!optionalMember.isPresent()) {
			return null;
		}
		Member member = optionalMember.get();
		System.out.println("member :" +member);
		CustomUserDetails detail = new CustomUserDetails(member,member.getRoleList());
		System.out.println(detail);
		return detail;
	}
	
	@Override
	public MemberTokenDto getloginToken(CustomUserDetails principal) {
		System.out.println("[API]");
		System.out.println("[JWT 토큰 생성] : 요청");
		System.out.println("[JWT 토큰 생성] : user = "+ principal);
		CustomUserDetails user = principal;
		String accessToken = TokenUtils.generateAccessToken(user);		
		user.setMemberRefreshToken(TokenUtils.generateRefreshToken(user));
		memberRepository.save(user.getMember());
		
		return new MemberTokenDto(principal.getUsername(), accessToken, user.getMemberRefreshToken());
	}
	
	
	@Override
	public List<String> getUserId(String userPhone, String userName) {
		List<Member> memberList = memberRepository.findAllByUserNameAndPhone(userName, userPhone);
		
		List<String> idList = new ArrayList<String>();
		for(Member member: memberList) {
			idList.add(member.getUserId());
		}
		return idList;
	}
}
