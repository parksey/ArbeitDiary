package com.arbietDiary.arbietdiary.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTokenDto {
	String userId;
	
	String accessToken;
	String refreshToken;
	
	public MemberTokenDto(String username, String accessToken, String refreshToken) {
		this.userId = username;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
