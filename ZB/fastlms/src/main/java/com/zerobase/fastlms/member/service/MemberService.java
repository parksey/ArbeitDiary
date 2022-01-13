package com.zerobase.fastlms.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService{
	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);
	
	boolean sendResetPassword(ResetPasswordInput parameter);
	
	boolean resetPassword(ResetPasswordInput parameter);
}
