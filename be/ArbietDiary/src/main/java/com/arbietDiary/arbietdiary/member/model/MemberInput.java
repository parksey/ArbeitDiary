package com.arbietDiary.arbietdiary.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInput {
	/*
	 * 회원 가입, 로그인, 비밀번호 초기화
	 */
	String userId;
	String userPassword;
	
	/*
	 * 회원 가입, 로그인
	 */
	String userName;
	
	/*
	 * 회원 가입
	 */
	String userPhone;
	String newPassword;
	
	/*
	 * 비밀번호 초기화 UUID
	 */
	String id;
}
