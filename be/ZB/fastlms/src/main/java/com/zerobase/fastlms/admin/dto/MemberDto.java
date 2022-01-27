package com.zerobase.fastlms.admin.dto;

import java.time.LocalDateTime;

import com.zerobase.fastlms.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
	String userId;
	 
	String userName;
	String phone;
	String password;
	LocalDateTime regDt;
	
	boolean emailAuthYn;
	String emailAuthKey;
	LocalDateTime emailAuthDt;
	
	String resetPasswordKey;
	LocalDateTime resetPasswordDt;
	
	boolean adminYn;
	String userState;
	
	long totalCount;
	long memberNum;
	
	public static MemberDto of(Member member) {
		/*
		MemberDto memberDto = new MemberDto();
		memberDto.setUserId(member.getUserId());
		memberDto.setUserName(member.getUserName());
		memberDto.setAdminYn(member.isAdminYn());
		memberDto.setEmailAuthDt(member.getEmailAuthDt());
		memberDto.setEmailAuthKey(member.getEmailAuthKey());
		memberDto.setEmailAuthYn(member.isEmailAuthYn());
		memberDto.setPassword(member.getPassword());
		memberDto.setPhone(member.getPhone());
		memberDto.setRegDt(member.getRegDt());
		*/
		
		return MemberDto.builder()
				.userId(member.getUserId())
				.userName(member.getUserName())
				//.password(member.getPassword())
				.phone(member.getPhone())
				.regDt(member.getRegDt())
				.emailAuthDt(member.getEmailAuthDt())
				.emailAuthKey(member.getEmailAuthKey())
				.emailAuthYn(member.isEmailAuthYn())
				.adminYn(member.isAdminYn())
				.userState(member.getUserState())
				.build();
	}
}
