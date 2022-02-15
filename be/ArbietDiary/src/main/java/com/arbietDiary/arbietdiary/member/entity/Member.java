package com.arbietDiary.arbietdiary.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.member.model.MemberCode;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Table(name = "member")
@Getter
@Setter
@Entity
public class Member implements MemberCode{
	@Id
	@Column(name = "user_id")
	String userId;
	
	@Column(nullable = false)
	String userName;
	
	String phone;
	
	@Column(nullable = false)
	String password;
	
	LocalDateTime regDt;
	
	boolean emailAuthYn;
	String emailAuthKey;
	LocalDateTime emailAuthDt;
	
	String resetPasswordKey;
	LocalDateTime resetPasswordDt;
	
	boolean adminYn;
	
	String userState;
	
	String roles;
	
	@Column(length = 1000)
	String refreshToken;
}
